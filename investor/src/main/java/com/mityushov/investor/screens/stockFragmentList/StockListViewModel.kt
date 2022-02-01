package com.mityushov.investor.screens.stockFragmentList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushov.investor.network.NetworkStatus
import com.mityushov.investor.data.StockRepository
import kotlinx.coroutines.launch

class StockListViewModel : ViewModel() {
    private val repository = StockRepository.get()
    val list = repository.list

    // TODO: 12.12.2021 снести все расчеты в repository
    fun getTotalProfit(): Float {
        var res = 0.0F
        for (item in list.value!!) {
            res += item.totalProfit
        }

        return res
    }

    fun getDailyProfit(): Float {
        var res = 0.0F

        for (item in list.value!!) {
            res += (item.dailyChange * item.amount.toFloat())
        }

        return res
    }

    // 1.11.21
    fun refreshScreen() {
        viewModelScope.launch {
            repository.refresh()
        }
    }
}