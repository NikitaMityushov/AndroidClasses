package com.mityushov.investor.database

import androidx.room.TypeConverter
import java.util.*

class StockTypeConverter {
    @TypeConverter
    fun toUUID(id: String?) : UUID? {
        return UUID.fromString(id)
    }
    @TypeConverter
    fun fromUUID(id: UUID?) : String? {
        return id?.toString()
    }
}