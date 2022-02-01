package com.mityushov.investor.data.local

import androidx.lifecycle.LiveData
import com.mityushov.investor.data.ICacheStockDataSource
import com.mityushov.investor.database.CacheStockDao
import com.mityushov.investor.models.CacheStockPurchase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class LocalCacheStockDataSource(
    private val cacheStockDao: CacheStockDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ICacheStockDataSource {
    override fun getCacheStockPurchaseFromId(id: UUID): LiveData<CacheStockPurchase> {
        return cacheStockDao.getStockFromId(id)
    }

    override suspend fun getAllStocks(): LiveData<List<CacheStockPurchase>> {
        return cacheStockDao.getAllStocks()
    }

    override suspend fun insertAll(cache: List<CacheStockPurchase>) {
        withContext(coroutineDispatcher) {
            cacheStockDao.insertAll(cache)
        }
    }

    override suspend fun invalidateCache() {
        withContext(coroutineDispatcher) {
            cacheStockDao.invalidateCache()
        }
    }
}