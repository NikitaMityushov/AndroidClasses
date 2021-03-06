package com.mityushov.investor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.StockPurchase
import java.util.*

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_purchases")
    fun getAllStocks(): List<StockPurchase>

    @Query("SELECT * FROM stock_purchases WHERE purchase_id=(:id)")
    fun getStockFromId(id: UUID): StockPurchase

    @Insert
    fun addStockPurchase(stock: StockPurchase)

    @Query("DELETE FROM stock_purchases WHERE purchase_id=(:id)")
    fun deleteStockFromId(id: UUID)

    @Delete
    fun deleteStock(stock: StockPurchase)

    @Update
    fun updateStock(stock: StockPurchase)
}
