package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushov.investor.data.IStockRepository
import java.util.*

class StockFragmentViewModelFactory(
    private val id: UUID,
    private val repository: IStockRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockFragmentViewModel::class.java)) {
            return StockFragmentViewModel(id, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}