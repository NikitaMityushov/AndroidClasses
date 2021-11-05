package com.mityushov.investor.models

import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import timber.log.Timber
import java.util.*

class StockCurrentStat(val stock: StockPurchase): StockAPI {
    private var currCurrency = 0.0F
    private var dailChange: Float = 0.0F
    private var dailChangePercent: Float = 0.0F
    private var name = ""

    init {
        // start coroutine
        runBlocking(context = Dispatchers.IO) {
            getCurCurrency()
        }
    }
    /*
        private methods
    */
    private fun getCurCurrency() {
        /**
         * Build request to www.cnbc.com with JSOUP
         */
        Timber.d("getCurCurrency() is called")
        val url = "https://www.cnbc.com/quotes/${stock.ticker}"
        Timber.d("URL is $url")
        /*
            need try catch!!
         */
        val doc = Jsoup.connect(url).get()
        val currentStat: Elements = doc.getElementsByClass("QuoteStrip-lastPrice")
        val openStat = doc.getElementsByClass("Summary-value")
        val name = doc.getElementsByClass("QuoteStrip-name")
        //val element: Elements = doc.getElementsByClass("QuoteStrip-lastPriceStripContainer")
        Timber.d("\n" + "current currency is: " + currentStat[0].text())
        Timber.d("\n" + "Open value is: " + openStat[0].text())
        Timber.d("\n" + "Corp name is: " + name[0].text())
        val openCurr = openStat[0].text().toFloat()
        currCurrency = currentStat[0].text().toFloat()
        dailChange = currCurrency - openCurr
        dailChangePercent = 100 * dailChange / openCurr
        this.name = name[0].text()
    }
    /*
        API methods
     */
    override fun getId(): UUID {
        return stock.id
    }

    override fun getName(): String {
        return name
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
        return stock.purchaseCurrency * stock.amount
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
        return 100 * ((stock.amount * currCurrency / (getPurchasePrice() + stock.purchaseTax)) - 1)
    }

    override fun getDividends(): Float {
        TODO("Not yet implemented")
    }
}