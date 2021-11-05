package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.models.StockCurrentStat
import com.mityushov.investor.models.StockPurchase
import java.util.*

class StockFragmentViewModel(private val id: UUID): ViewModel() {
    private val repository = StockRepository.get()
    private val _data = MutableLiveData<StockAPI>()
    val data: LiveData<StockAPI>
        get() {
            return _data
        }

    init {
        _data.value = getData()
    }

    private fun getData(): StockAPI {
        return repository.getStockFromId(id)
    }

    fun deleteStock() {
        repository.deleteStockPurchase(id)
    }

    fun getStockPurchase(): StockPurchase {
        return (data.value as StockCurrentStat).stock
    }


}