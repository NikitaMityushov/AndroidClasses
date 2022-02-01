package com.mityushov.investor.data.remote

import com.mityushov.investor.data.IRemoteDataSource
import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.RemoteServiceStockAPI
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.network.CnbcService

class RemoteStockDataSource : IRemoteDataSource {
    private val service: RemoteServiceStockAPI = CnbcService

    override suspend fun getCacheStockPurchase(stock: StockPurchase): CacheStockPurchase {
        return service.getResponse(stock)
    }
}