package com.mityushov.investor.database

import android.content.Context
import androidx.room.Room
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.models.StockCurrentStat
import com.mityushov.investor.models.StockPurchase
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "stocks"

class StockRepository private constructor(context: Context) {
    private val database: StockDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            StockDatabase::class.java,
            DATABASE_NAME)
            .build()
    }

    private val stockDao = database.stockDao()
// api
    /*
    fun getAllStocks() = stockDao.getAllStocks()

    fun getStockFromId(id: UUID) = stockDao.getStockFromId(id)

     */

    // api for debug
    private val list = mutableListOf<StockAPI>()

    init {
        init()
    }

    private fun init() {
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
    }

    fun getAllStocks(): List<StockAPI> {
        return list
    }

    fun getStockFromId(id: UUID): StockAPI {
        return list.filter { it.getId() == id }[0]
    }
    
    fun addStockPurchase(stock: StockPurchase): Boolean {
        val item = StockCurrentStat(stock)
        return if (item.getCurrentCurrency() != null) {
            list.add(item)
            true
        } else {
            false
        }
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