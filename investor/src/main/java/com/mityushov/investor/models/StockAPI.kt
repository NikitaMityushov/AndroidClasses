package com.mityushov.investor.models

import java.util.*

interface StockAPI {
    fun getId(): UUID
    fun getName(): String
    fun getTicker(): String
    fun getAmount(): Int
    fun getPurchaseCurrency(): Float
    fun getPurchasePrice(): Float
    fun getPurchaseTax(): Float
    fun getCurrentCurrency(): Float
    fun getDailyChange(): Float
    fun getDailyChangeInPercent(): Float
    fun getCurrentPrice(): Float
    fun getTotalProfit(): Float
    fun getProfitability(): Float
    fun getDividends(): Float
}