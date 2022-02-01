package com.mityushov.investor.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.network.NetworkStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.IllegalStateException
import java.util.*

class StockRepository private constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val localStockDataSource: IStockDataSource,
    private val localCacheStockDataSource: ICacheStockDataSource,
    private val remoteStockDataSource: IRemoteDataSource
) : IStockRepository {

    // 1) Cache list
    var list: LiveData<List<CacheStockPurchase>> = getAllStocks()

    // 2) Network status
    private val _status = MutableLiveData<NetworkStatus>()

    override val status: LiveData<NetworkStatus>
        get() {
            return _status
        }

    // 3) Refresh Cache
    override suspend fun refresh() {
        Timber.d("Refresh is called")
        _status.value = NetworkStatus.LOADING
        withContext(coroutineDispatcher) {
//            val stockList = stockDao.getAllStocks()
            val stockList = localStockDataSource.getAllStocks()
            val cache =
                stockList.map { item -> remoteStockDataSource.getCacheStockPurchase(item) }
                    .toList()
            localCacheStockDataSource.insertAll(cache)
        }
        _status.value = NetworkStatus.DONE
    }

    override suspend fun getStockFromId(id: UUID): StockPurchase {
        return localStockDataSource.getStockFromId(id)
    }

    override suspend fun addStockPurchase(stock: StockPurchase): Boolean {
        val item = remoteStockDataSource.getCacheStockPurchase(stock)
        Timber.d("Ticker's name is ${item.name}")
        // костыль, проверяющий криво, существует ли такой тикер, надо че то по лучше придумать!!!
        return if (item.name != "") {
            Timber.d("addStock in name != \"\"")
            localStockDataSource.addStockPurchase(stock)
            refresh()
            true
        } else {
            Timber.d("addStock in else branch")
            false
        }
    }

    override suspend fun deleteStockPurchaseFromId(id: UUID) {
        localStockDataSource.deleteStockPurchaseFromId(id)
        localCacheStockDataSource.invalidateCache()
        refresh()
    }

    override suspend fun deleteStock(stockPurchase: StockPurchase) {
        localStockDataSource.deleteStock(stockPurchase)
    }

    override suspend fun updateStockPurchase(stockPurchase: StockPurchase) {
        Timber.d("updateStockPurchase is called, ticker is ${stockPurchase.ticker}")
        localStockDataSource.updateStockPurchase(stockPurchase)
        localCacheStockDataSource.invalidateCache()
        refresh()
    }

    override suspend fun getCacheStockPurchaseFromId(id: UUID): LiveData<CacheStockPurchase> {
        return localCacheStockDataSource.getCacheStockPurchaseFromId(id)
    }

    private fun getAllStocks(): LiveData<List<CacheStockPurchase>> = runBlocking {
        return@runBlocking localCacheStockDataSource.getAllStocks()
    }


    // singleton
    companion object {
        @Volatile
        private var instance: StockRepository? = null

        fun init(
            coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
            localStockDataSource: IStockDataSource,
            localCacheStockDataSource: ICacheStockDataSource,
            remoteStockDataSource: IRemoteDataSource
        ) {

            if (instance == null) {
                instance = StockRepository(
                    coroutineDispatcher = coroutineDispatcher,
                    localStockDataSource = localStockDataSource,
                    localCacheStockDataSource = localCacheStockDataSource,
                    remoteStockDataSource = remoteStockDataSource
                )
            }
        }

        fun get(): StockRepository {
            return instance ?: throw IllegalStateException("StockRepository must by initialized")
        }
    }
}
