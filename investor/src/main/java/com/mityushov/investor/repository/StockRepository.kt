package com.mityushov.investor.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.mityushov.investor.database.StockDatabase
import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.network.CnbcService
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.network.NetworkStatus
import com.mityushov.investor.network.asCacheNetworkStockPurchase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "stocks"

class StockRepository private constructor(context: Context) {
    // 1) Database
    private val database: StockDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            StockDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }

    // 2) DAO
    private val stockDao = database.stockDao()

    private val cacheStockDao = database.cacheStockDao()

    // 3) Cache list
    var list = cacheStockDao.getAllStocks()

    // 4) Network status
    private val _status = MutableLiveData<NetworkStatus>()

    val status: LiveData<NetworkStatus>
        get() {
            return _status
        }

    // 5) Refresh Cache
    suspend fun refresh() {
        Timber.d("Refresh is called")
        _status.value = NetworkStatus.LOADING
        withContext(Dispatchers.IO) {
            val stockList = stockDao.getAllStocks()
            val cache =
                stockList.map { item -> CnbcService(item).stockDto.asCacheNetworkStockPurchase() }
                    .toList()
            cacheStockDao.insertAll(cache)
        }
        _status.value = NetworkStatus.DONE
    }

    fun getStockFromId(id: UUID): StockPurchase {
        return stockDao.getStockFromId(id)
    }

    fun addStockPurchase(stock: StockPurchase): Boolean {
        val item = CnbcService(stock)
        // костыль, проверяющий криво, существует ли такой тикер, надо че то по лучше придумать!!!
        return if (item.getCurrentCurrency() != null) {
            // 31.10.21
            runBlocking(context = Dispatchers.IO) {
                stockDao.addStockPurchase(stock)
                refresh()
            }
            //
            true
        } else {
            false
        }
    }

    fun deleteStockPurchaseFromId(id: UUID) {
        runBlocking(context = Dispatchers.IO) {
            stockDao.deleteStockFromId(id)
            cacheStockDao.invalidateCache()
            refresh()
        }
    }

    fun deleteStock(stockPurchase: StockPurchase) {
        runBlocking(Dispatchers.IO) {
            stockDao.deleteStock(stockPurchase)
        }
    }

    fun updateStockPurchase(stockPurchase: StockPurchase) {
        Timber.d("updateStockPurchase is called, ticker is ${stockPurchase.ticker}")
        runBlocking(context = Dispatchers.IO) {
            stockDao.updateStock(stockPurchase)
            cacheStockDao.invalidateCache()
            refresh()
        }
    }

    fun getCacheStockPurchaseFromId(id: UUID): LiveData<CacheStockPurchase> {
        return database.cacheStockDao().getStockFromId(id)
    }


    // singleton
    companion object {
        private var instance: StockRepository? = null

        fun init(context: Context) {
            if (instance == null) {
                instance = StockRepository(context)
            }
        }

        fun get(): StockRepository {
            return instance ?: throw IllegalStateException("StockRepository must by initialized")
        }
    }
}
