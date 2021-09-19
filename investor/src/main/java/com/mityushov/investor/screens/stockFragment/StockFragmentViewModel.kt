package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.ViewModel
import com.mityushov.investor.database.StockRepository
import com.mityushov.investor.models.StockAPI
import java.util.*

class StockFragmentViewModel: ViewModel() {

    private val repository = StockRepository.get()

    fun getData(id: UUID): StockAPI {
        return repository.getStockFromId(id)
    }
}