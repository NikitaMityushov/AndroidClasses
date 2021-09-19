package com.mityushov.investor.screens.stockFragmentList

import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.models.StockCurrentStat
import com.mityushov.investor.models.StockPurchase

class StockListViewModel: ViewModel() {
    private val repository = StockRepository.get()
    private val list = repository.getAllStocks()

    fun getData(): List<StockAPI> {
        return repository.getAllStocks()
    }

    fun getTotalProfit(): Float {
        var res = 0.0F
        for(item in list) {
            res += item.getTotalProfit()
        }

        return res
    }

    fun getDailyProfit(): Float {
        var res = 0.0F

        for (item in list) {
            res += (item.getDailyChange() * item.getAmount().toFloat())
        }

        return res
    }
}