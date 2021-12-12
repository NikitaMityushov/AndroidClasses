package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.ViewModel
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.models.asStockPurchase
import com.mityushov.investor.repository.StockRepository
import timber.log.Timber
import java.util.*

class StockFragmentViewModel(private val id: UUID) : ViewModel() {
    private val repository = StockRepository.get()
    val data = repository.getCacheStockPurchaseFromId(id)

    fun deleteStock() {
        repository.deleteStockPurchase(id)
    }

    fun getStockPurchase(): StockPurchase {
        Timber.d("StockFragmentViewModel getStockPurchase is called")
        return data.value.asStockPurchase()
    }

}
