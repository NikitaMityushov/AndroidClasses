package com.mityushov.investor.screens.buyStockWindow

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockPurchase
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
}