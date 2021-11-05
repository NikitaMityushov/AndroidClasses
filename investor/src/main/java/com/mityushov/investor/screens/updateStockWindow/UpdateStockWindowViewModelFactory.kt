package com.mityushov.investor.screens.updateStockWindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushov.investor.models.StockPurchase

class UpdateStockWindowViewModelFactory(private val stockPurchase: StockPurchase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateStockWindowViewModel::class.java)) {
            return UpdateStockWindowViewModel(stockPurchase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}