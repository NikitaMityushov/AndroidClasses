package com.mityushov.investor.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "cache_stock_purchases")
data class CacheStockPurchase(
    @PrimaryKey
    @ColumnInfo(name = "purchase_id")
    val id: UUID,
    @ColumnInfo(name = "ticker")
    val ticker: String,
    @ColumnInfo(name = "amount")
    val amount: Int,
    @ColumnInfo(name = "purchase_currency")
    val purchaseCurrency: Float,
    @ColumnInfo(name = "purchase_price")
    val purchasePrice: Float,
    @ColumnInfo(name = "purchase_tax")
    val purchaseTax: Float,
    @ColumnInfo(name = "current_currency")
    val currentCurrency: Float,
    @ColumnInfo(name = "daily_change")
    val dailyChange: Float,
    @ColumnInfo(name = "daily_change_percent")
    val dailyChangePercent: Float,
    @ColumnInfo(name = "current_price")
    val currentPrice: Float,
    @ColumnInfo(name = "total_profit")
    val totalProfit: Float,
    @ColumnInfo(name = "profitability")
    val profitability: Float,
    @ColumnInfo(name = "dividends")
    val dividends: Float,
    @ColumnInfo(name = "name")
    val name: String
) : Serializable

/*
    Extension functions
 */
fun CacheStockPurchase?.asStockPurchase(): StockPurchase {
    return StockPurchase(
        id = this!!.id,
        ticker = ticker,
        amount = amount,
        purchaseCurrency = purchaseCurrency,
        purchaseTax = purchaseTax
    )
}