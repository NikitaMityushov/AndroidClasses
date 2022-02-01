package com.mityushov.investor.data.local

import com.mityushov.investor.data.IStockDataSource
import com.mityushov.investor.database.StockDao
import com.mityushov.investor.models.StockPurchase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class LocalStockDataSource(
    private val stockDao: StockDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IStockDataSource {
    override suspend fun getStockFromId(id: UUID): StockPurchase = withContext(coroutineDispatcher) {
        return@withContext stockDao.getStockFromId(id)
    }

    override suspend fun addStockPurchase(stock: StockPurchase) = withContext(coroutineDispatcher) {
        stockDao.addStockPurchase(stock)
    }

    override suspend fun deleteStockPurchaseFromId(id: UUID) = withContext(coroutineDispatcher) {
        stockDao.deleteStockFromId(id)
    }

    override suspend fun deleteStock(stockPurchase: StockPurchase) = withContext(coroutineDispatcher) {
        stockDao.deleteStock(stockPurchase)
    }

    override suspend fun updateStockPurchase(stockPurchase: StockPurchase) = withContext(coroutineDispatcher) {
        stockDao.updateStock(stockPurchase)
    }

    override suspend fun getAllStocks(): List<StockPurchase> = withContext(coroutineDispatcher) {
        return@withContext stockDao.getAllStocks()
    }

}