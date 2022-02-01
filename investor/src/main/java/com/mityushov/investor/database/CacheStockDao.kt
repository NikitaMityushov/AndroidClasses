package com.mityushov.investor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mityushov.investor.models.CacheStockPurchase
import java.util.*

@Dao
interface CacheStockDao {
    @Query("SELECT * FROM cache_stock_purchases")
    fun getAllStocks(): LiveData<List<CacheStockPurchase>>

    @Query("DELETE FROM cache_stock_purchases")
    fun invalidateCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cache: List<CacheStockPurchase>)

    @Query("SELECT * FROM cache_stock_purchases WHERE purchase_id=(:id)")
    fun getStockFromId(id: UUID): LiveData<CacheStockPurchase>
}