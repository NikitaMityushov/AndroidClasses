package com.mityushov.investor.models

import android.util.Log
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.*

private const val TAG: String = "StockPurchase tag"

class StockPurchase(val id: UUID = UUID.randomUUID(),
                    val name: String = "",
                    val ticker: String = "",
                    val amount: Int = 0,
                    val purchaseCurrency: Float = 0.0F,
                    val purchasePrice: Float = 0.0F,
                    val purchaseTax: Float = 0.0F) {
    var currCurrency = 0.0F
    var currPrice: Float = 0.0F
    var ttlProfit: Float = 0.0F
    var profitblty: Float = 0.0F
    var dividends: Float = 0.0F

    init {
        this.update()
    }

    private fun getCurrentCurrency(): Float {
        Log.d(TAG, "getCurrentCurrency() is called")
        val url = "https://www.cnbc.com/quotes/${ticker}"
        val doc = Jsoup.connect(url).get()
        val element: Elements = doc.getElementsByClass("QuoteStrip-lastPrice")
        Log.d(TAG, "\ncurrent currency is: ${element[0].text()}")

        return element[0].text().toFloat()
    }

    private fun getCurrentPrice(): Float {
        Log.d(TAG, "get Current price is called\nCurrent price is ${amount * currCurrency}")
        return amount * currCurrency
    }

    private fun getTotalProfit(): Float {
        Log.d(TAG, "getTotalProfit() is called\nTotal profit is ${amount * (currCurrency - purchaseCurrency) - purchaseTax}")
        return amount * (currCurrency - purchaseCurrency) - purchaseTax
    }

    private fun getProfitability(): Float {
        Log.d(TAG, "getProfitability() is called\nProfitability is ${100 * (amount * currCurrency / (purchasePrice + purchaseTax) - 1)}")
        return 100 * (amount * currCurrency / (purchasePrice + purchaseTax) - 1)
    }

    fun update() {
        runBlocking {
            // blocking IO
            withContext(Dispatchers.IO) {
                currCurrency = getCurrentCurrency()
            }
            // with default dispatcher, asynchronous. All tasks depend on currCurrency field
            launch {
                currPrice = getCurrentPrice()
            }

            launch {
                ttlProfit = getTotalProfit()
            }

            launch {
                profitblty = getProfitability()
            }
        }
    }
}