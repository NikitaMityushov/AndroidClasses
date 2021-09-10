package com.mityushov.investor.models

import org.junit.Test

import org.junit.Assert.*

class StockPurchaseTest {
    val instance: StockPurchase = StockPurchase(name = "Apple Inc",
                                                amount = 9,
                                                purchaseCurrency = 130F,
                                                purchasePrice = 1170F,
                                                purchaseTax = 1.2F,
                                                ticker = "AAPL")

    @Test
    fun getCurrCurrency() {
        assert(instance.amount == 9)
    }

    @Test
    fun setCurrCurrency() {
    }

    @Test
    fun getCurrPrice() {
        assert(instance.purchasePrice == 1170F)
    }

    @Test
    fun setCurrPrice() {
    }

    @Test
    fun getTtlProfit() {

    }

    @Test
    fun setTtlProfit() {
    }

    @Test
    fun getProfitblty() {
    }

    @Test
    fun setProfitblty() {
    }

    @Test
    fun getDividends() {
    }

    @Test
    fun setDividends() {
    }

    @Test
    fun update() {
    }

    @Test
    fun getId() {
    }

    @Test
    fun getName() {
    }

    @Test
    fun getTicker() {
    }

    @Test
    fun getAmount() {
    }

    @Test
    fun getPurchaseCurrency() {
    }

    @Test
    fun getPurchasePrice() {
    }

    @Test
    fun getPurchaseTax() {
    }
}