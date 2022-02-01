package com.mityushov.investor.screens.updateStockWindow

import androidx.lifecycle.ViewModel
import com.mityushov.investor.data.IStockRepository
import com.mityushov.investor.models.StockPurchase
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class UpdateStockWindowViewModel(
    private val stock: StockPurchase,
    private val repository: IStockRepository
) : ViewModel() {
    // api
    fun updateStockPurchase() = runBlocking {
        Timber.d("updateStockPurchase is called from viewModel, stock is $stock")
        repository.updateStockPurchase(stock)
    }
}