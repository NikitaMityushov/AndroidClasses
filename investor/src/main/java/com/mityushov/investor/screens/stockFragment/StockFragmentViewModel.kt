package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockAPI
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


}