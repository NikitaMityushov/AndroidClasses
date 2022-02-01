package com.mityushov.investor.screens.buyStockWindow

import androidx.lifecycle.ViewModel
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.data.StockRepository
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class BuyStockWindowViewModel: ViewModel() {
    private val repository = StockRepository.get()

    fun addStock(stock: StockPurchase): Boolean {
        var result: Boolean
        try {
            runBlocking {
                result = repository.addStockPurchase(stock)
            }
        } catch (e: Exception) {
            return false
        }

        return result
    }
}