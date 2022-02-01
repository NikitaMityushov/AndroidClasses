package com.mityushov.investor.data

import androidx.lifecycle.LiveData
import com.mityushov.investor.models.CacheStockPurchase
import java.util.*

interface ICacheStockDataSource {
    fun getCacheStockPurchaseFromId(id: UUID): LiveData<CacheStockPurchase>
    suspend fun getAllStocks(): LiveData<List<CacheStockPurchase>>
    suspend fun insertAll(cache: List<CacheStockPurchase>)
    suspend fun invalidateCache()
}