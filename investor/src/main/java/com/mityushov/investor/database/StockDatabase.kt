package com.mityushov.investor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mityushov.investor.models.StockPurchase

@Database(entities = [StockPurchase::class], version = 1)
@TypeConverters(StockTypeConverter::class)
abstract class StockDatabase: RoomDatabase() {
    abstract fun stockDao(): StockDao
}