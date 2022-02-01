package com.mityushov.investor.data

import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.StockPurchase

interface IRemoteDataSource {
    suspend fun getCacheStockPurchase(stock: StockPurchase): CacheStockPurchase
}