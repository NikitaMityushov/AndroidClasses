package com.mityushov.investor.screens.buyStockWindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.repository.StockRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class BuyStockWindowViewModel: ViewModel() {
    private val repository = StockRepository.get()

    fun addStock(stock: StockPurchase): Boolean {
        return try {
            repository.addStockPurchase(stock)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteStock(stock: StockPurchase) {
        viewModelScope.launch {
            repository.deleteStock(stock)
        }
    }
}