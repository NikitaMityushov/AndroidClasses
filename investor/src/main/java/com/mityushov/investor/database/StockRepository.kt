package com.mityushov.investor.database

import android.content.Context
import androidx.room.Room
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.network.CnbcService
import com.mityushov.investor.models.StockPurchase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
            DATABASE_NAME)
            .build()
    }
    // 2) DAO
    private val stockDao = database.stockDao()

    private val list = mutableListOf<StockAPI>()

    init {
        refresh()
    }

    fun refresh() {
        runBlocking(context = Dispatchers.IO) {
            val ls = stockDao.getAllStocks()

            list.clear()

            for (item in ls) {
                val currentStock = CnbcService(item)
                list.add(currentStock)
            }
        }
    }

    fun getAllStocks(): List<StockAPI> {
        return list
    }

    fun getStockFromId(id: UUID): StockAPI {
        return list.filter { it.getId() == id }[0]
    }
    
    fun addStockPurchase(stock: StockPurchase): Boolean {
        val item = CnbcService(stock)
        // костыль, проверяющий криво, существует ли такой тикер, надо че то по лучше придумать!!!
        return if (item.getCurrentCurrency() != null) {
            // 31.10.21
            runBlocking(context = Dispatchers.IO) {
                stockDao.addStockPurchase(stock)
            }
            refresh()
            //
            true
        } else {
            false
        }
    }

    fun deleteStockPurchase(id: UUID) {
        runBlocking(context = Dispatchers.IO) {
            stockDao.deleteStockFromId(id)
        }
        refresh()
    }

    fun updateStockPurchase(stockPurchase: StockPurchase) {
        Timber.d("updateStockPurchase is called, ticker is ${stockPurchase.ticker}")
        runBlocking(context = Dispatchers.IO) {
            stockDao.updateStock(stockPurchase)
        }
        refresh()
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



/* 31.10.21
val aapl = StockCurrentStat(StockPurchase(
    // name = "Apple inc",
    ticker = "AAPL",
    amount = 9,
    purchaseCurrency = 144.0F,
    purchaseTax = 20.31F))

val msft = StockCurrentStat(StockPurchase(
    // name = "Microsoft inc",
    ticker = "MSFT",
    amount = 10,
    purchaseCurrency = 256.19F,
    purchaseTax = 6.0F))

val epam = StockCurrentStat(StockPurchase(
    // name = "EPAM systems",
    ticker = "EPAM",
    amount = 5,
    purchaseCurrency = 431.0F,
    purchaseTax = 6.0F))

val tmus = StockCurrentStat(StockPurchase(
    // name = "T-Mobile",
    ticker = "TMUS",
    amount = 11,
    purchaseCurrency = 133.05F,
    purchaseTax = 6.0F))

list.add(aapl)
list.add(msft)
list.add(epam)
list.add(tmus)
 */