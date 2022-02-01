package com.mityushov.investor.data

import com.mityushov.investor.models.StockPurchase
import java.util.*

interface IStockDataSource {
    suspend fun getStockFromId(id: UUID): StockPurchase
    suspend fun addStockPurchase(stock: StockPurchase)
    suspend fun deleteStockPurchaseFromId(id: UUID)
    suspend fun deleteStock(stockPurchase: StockPurchase)
    suspend fun updateStockPurchase(stockPurchase: StockPurchase)
    suspend fun getAllStocks(): List<StockPurchase>
}