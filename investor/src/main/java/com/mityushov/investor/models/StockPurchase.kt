package com.mityushov.investor.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "stock_purchases")
data class StockPurchase(@PrimaryKey @ColumnInfo(name = "purchase_id") val id: UUID = UUID.randomUUID(),
                    @ColumnInfo(name = "ticker") val ticker: String = "",
                    @ColumnInfo(name = "amount") var amount: Int = 0,
                    @ColumnInfo(name = "purchase_currency") var purchaseCurrency: Float = 0.0F,
                    @ColumnInfo(name = "purchase_tax") var purchaseTax: Float = 0.0F): Serializable