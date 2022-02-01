package com.mityushov.investor.data

import androidx.lifecycle.LiveData
import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.network.NetworkStatus
import java.util.*

interface IStockRepository {
    val status: LiveData<NetworkStatus>
    suspend fun refresh()
    suspend fun getStockFromId(id: UUID): StockPurchase
    suspend fun addStockPurchase(stock: StockPurchase): Boolean
    suspend fun deleteStockPurchaseFromId(id: UUID)
    suspend fun deleteStock(stockPurchase: StockPurchase)
    suspend fun updateStockPurchase(stockPurchase: StockPurchase)
    suspend fun getCacheStockPurchaseFromId(id: UUID): LiveData<CacheStockPurchase>
}