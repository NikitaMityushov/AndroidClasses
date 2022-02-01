package com.mityushov.investor.network

import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.StockPurchase

data class StockDto(
    val purchasePrice: Float,
    val stockPurchase: StockPurchase,
    val currentCurrency: Float,
    val dailyChange: Float,
    val dailyChangePercent: Float,
    val currentPrice: Float,
    val totalProfit: Float,
    val profitability: Float,
    val dividends: Float,
    val name: String
)

// Extension
fun StockDto.asCacheStockPurchase(): CacheStockPurchase {
    return CacheStockPurchase(
        id = stockPurchase.id,
        ticker = stockPurchase.ticker,
        amount = stockPurchase.amount,
        purchaseCurrency = stockPurchase.purchaseCurrency,
        purchasePrice = purchasePrice,
        purchaseTax = stockPurchase.purchaseTax,
        currentCurrency = currentCurrency,
        dailyChange = dailyChange,
        dailyChangePercent = dailyChangePercent,
        currentPrice = currentPrice,
        totalProfit = totalProfit,
        profitability = profitability,
        dividends = dividends,
        name = name
    )
}