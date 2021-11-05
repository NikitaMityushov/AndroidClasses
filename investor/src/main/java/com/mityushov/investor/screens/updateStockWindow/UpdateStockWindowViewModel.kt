package com.mityushov.investor.screens.updateStockWindow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.models.StockPurchase
import timber.log.Timber
import java.util.*

class UpdateStockWindowViewModel(private val stock: StockPurchase): ViewModel() {
    private val repository = StockRepository.get()

    // api
    fun updateStockPurchase() {
        Timber.d("updateStockPurchase is called from viewModel")
        repository.updateStockPurchase(stock)
    }


}