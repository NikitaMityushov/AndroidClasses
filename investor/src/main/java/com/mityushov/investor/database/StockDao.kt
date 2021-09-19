package com.mityushov.investor.database

import androidx.room.Dao
import androidx.room.Query
import com.mityushov.investor.models.StockPurchase
import java.util.*

@Dao
interface StockDao {
    @Query("SELECT * FROM stock_purchases")
    fun getAllStocks(): List<StockPurchase>
    @Query("SELECT * FROM stock_purchases WHERE purchase_id=(:id)")
    fun getStockFromId(id: UUID): StockPurchase
}