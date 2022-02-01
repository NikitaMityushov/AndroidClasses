package com.mityushov.investor.network

import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.models.RemoteServiceStockAPI
import com.mityushov.investor.models.StockPurchase
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import timber.log.Timber

private const val BASE_URL = "https://www.cnbc.com/quotes/"

object CnbcService : RemoteServiceStockAPI {
    private var currCurrency = 0.0F
    private var dailChange: Float = 0.0F
    private var dailChangePercent: Float = 0.0F
    private var name = ""
    private lateinit var stockDto: StockDto

    /*
        private methods
    */
    private fun getCurCurrency(stockPurchase: StockPurchase) {
        /**
         * Build request to www.cnbc.com with JSOUP
         */
        Timber.d("getCurCurrency() is called")
        val url = "$BASE_URL${stockPurchase.ticker}"
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

    private fun getName(): String {
        return name
    }

    private fun getPurchasePrice(stockPurchase: StockPurchase): Float {
        return stockPurchase.purchaseCurrency * stockPurchase.amount
    }

    private fun getCurrentPrice(stockPurchase: StockPurchase): Float {
        return stockPurchase.amount * currCurrency
    }

    private fun getTotalProfit(stockPurchase: StockPurchase): Float {
        return stockPurchase.amount * (currCurrency - stockPurchase.purchaseCurrency) - stockPurchase.purchaseTax
    }

    private fun getProfitability(stockPurchase: StockPurchase): Float {
        return 100 * ((stockPurchase.amount * currCurrency / (getPurchasePrice(stockPurchase) + stockPurchase.purchaseTax)) - 1)
    }

    private fun getDividends(): Float {
        // TODO("Not yet implemented")
        return 0.0F
    }
    /*
        public methods
     */
    override suspend fun getResponse(stockPurchase: StockPurchase): CacheStockPurchase {
        // start coroutine
        withContext(context = Dispatchers.IO) {
            getCurCurrency(stockPurchase)
        }
        stockDto = StockDto(
            stockPurchase = stockPurchase,
            purchasePrice = getPurchasePrice(stockPurchase),
            currentCurrency = currCurrency,
            dailyChange = dailChange,
            dailyChangePercent = dailChangePercent,
            currentPrice = getCurrentPrice(stockPurchase),
            totalProfit = getTotalProfit(stockPurchase),
            profitability = getProfitability(stockPurchase),
            dividends = getDividends(),
            name = getName()
        )
        return stockDto.asCacheStockPurchase()
    }
}