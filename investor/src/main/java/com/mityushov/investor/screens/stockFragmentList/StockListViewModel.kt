package com.mityushov.investor.screens.stockFragmentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockAPI

class StockListViewModel: ViewModel() {
    private val repository = StockRepository.get()
    private val _list = MutableLiveData<List<StockAPI>>()
    val list: LiveData<List<StockAPI>>
        get() {
            return _list
        }

    init {
        _list.value = getData()
    }


    private fun getData(): List<StockAPI> {
        return repository.getAllStocks()
    }

    fun getTotalProfit(): Float {
        var res = 0.0F
        for(item in _list.value!!) {
            res += item.getTotalProfit()
        }

        return res
    }

    fun getDailyProfit(): Float {
        var res = 0.0F

        for (item in _list.value!!) {
            res += (item.getDailyChange() * item.getAmount().toFloat())
        }

        return res
    }
    // 1.11.21
    fun refreshScreen() {
        repository.refresh()
        _list.value = getData()
    }
}