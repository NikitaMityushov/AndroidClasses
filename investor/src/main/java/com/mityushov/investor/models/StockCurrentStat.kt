package com.mityushov.investor.models

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.*

private const val TAG: String = "StockCurrentStat tag"

class StockCurrentStat(private val stock: StockPurchase): StockAPI {

    private var currCurrency = 0.0F
    private var dailChange: Float = 0.0F
    private var dailChangePercent: Float = 0.0F

    init {
        runBlocking {
            // blocking IO
            withContext(Dispatchers.IO) {
                getCurCurrency()
            }
        }
    }
    /*
        private methods
    */
    private fun getCurCurrency() {
        /**
         * Build request to www.cnbc.com with JSOUP
         */
        Log.d(TAG, "getCurCurrency() is called")
        val url = "https://www.cnbc.com/quotes/${stock.ticker}"
        val doc = Jsoup.connect(url).get()
        val currentStat: Elements = doc.getElementsByClass("QuoteStrip-lastPrice")
        val openStat = doc.getElementsByClass("Summary-value")
        //val element: Elements = doc.getElementsByClass("QuoteStrip-lastPriceStripContainer")
        Log.d(TAG, "\ncurrent currency is: ${currentStat[0].text()}")
        Log.d(TAG, "\nOpen value is: ${openStat[0].text()}")
        val openCurr = openStat[0].text().toFloat()
        currCurrency = currentStat[0].text().toFloat()
        dailChange = currCurrency - openCurr
        dailChangePercent = 100 * dailChange / openCurr
    }
    /*
        API methods
     */
    override fun getId(): UUID {
        return stock.id
    }

    override fun getName(): String {
        return stock.name
    }

    override fun getTicker(): String {
        return stock.ticker
    }

    override fun getAmount(): Int {
        return stock.amount
    }

    override fun getPurchaseCurrency(): Float {
        return stock.purchaseCurrency
    }

    override fun getPurchasePrice(): Float {
        return stock.purchaseCurrency + stock.amount
    }

    override fun getPurchaseTax(): Float {
        return stock.purchaseTax
    }

    override fun getCurrentCurrency(): Float {
        return currCurrency
    }

    override fun getDailyChange(): Float {
        return dailChange
    }

    override fun getDailyChangeInPercent(): Float {
        return dailChangePercent
    }

    override fun getCurrentPrice(): Float {
        return stock.amount * currCurrency
    }

    override fun getTotalProfit(): Float {
        return stock.amount * (currCurrency - stock.purchaseCurrency) - stock.purchaseTax
    }

    override fun getProfitability(): Float {
        return 100 * (stock.amount * currCurrency / (getPurchasePrice() + stock.purchaseTax) - 1)
    }

    override fun getDividends(): Float {
        TODO("Not yet implemented")
    }
}