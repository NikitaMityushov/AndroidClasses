package com.mityushov.investor.models

interface RemoteServiceStockAPI {
    suspend fun getResponse(stockPurchase: StockPurchase) : CacheStockPurchase
}