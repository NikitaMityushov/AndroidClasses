package com.mityushov.investor.network

import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.models.StockPurchase
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import timber.log.Timber
import java.util.*

private const val BASE_URL = "https://www.cnbc.com/quotes/"

class CnbcService(private val stockPurchase: StockPurchase) : StockAPI {
    private var currCurrency = 0.0F
    private var dailChange: Float = 0.0F
    private var dailChangePercent: Float = 0.0F
    private var name = ""
    val stockDto: StockDto

    init {
        // start coroutine
        runBlocking(context = Dispatchers.IO) {
            getCurCurrency()
        }
        stockDto = StockDto(
            stockPurchase = stockPurchase,
            purchasePrice = getPurchasePrice(),
            currentCurrency = currCurrency,
            dailyChange = dailChange,
            dailyChangePercent = dailChangePercent,
            currentPrice = getCurrentPrice(),
            totalProfit = getTotalProfit(),
            profitability = getProfitability(),
            dividends = getDividends(),
            name = getName()
        )
    }
    /*
        private methods
    */
    private fun getCurCurrency() {
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
    override fun getId(): UUID {
        return stockPurchase.id
    }

    override fun getName(): String {
        return name
    }

    override fun getTicker(): String {
        return stockPurchase.ticker
    }

    override fun getAmount(): Int {
        return stockPurchase.amount
    }

    override fun getPurchaseCurrency(): Float {
        return stockPurchase.purchaseCurrency
    }

    override fun getPurchasePrice(): Float {
        return stockPurchase.purchaseCurrency * stockPurchase.amount
    }

    override fun getPurchaseTax(): Float {
        return stockPurchase.purchaseTax
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
        return stockPurchase.amount * currCurrency
    }

    override fun getTotalProfit(): Float {
        return stockPurchase.amount * (currCurrency - stockPurchase.purchaseCurrency) - stockPurchase.purchaseTax
    }

    override fun getProfitability(): Float {
        return 100 * ((stockPurchase.amount * currCurrency / (getPurchasePrice() + stockPurchase.purchaseTax)) - 1)
    }

    override fun getDividends(): Float {
        // TODO("Not yet implemented")
        return 0.0F
    }

    override fun getStockPurchase(): StockPurchase {
        return stockPurchase
    }
}