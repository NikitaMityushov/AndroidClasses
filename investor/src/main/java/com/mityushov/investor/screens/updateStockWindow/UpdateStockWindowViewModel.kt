package com.mityushov.investor.screens.updateStockWindow

import androidx.lifecycle.ViewModel
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.repository.StockRepository
import timber.log.Timber

class UpdateStockWindowViewModel(private val stock: StockPurchase): ViewModel() {
    private val repository = StockRepository.get()

    // api
    fun updateStockPurchase() {
        Timber.d("updateStockPurchase is called from viewModel, stock is $stock")
        repository.updateStockPurchase(stock)
    }

}