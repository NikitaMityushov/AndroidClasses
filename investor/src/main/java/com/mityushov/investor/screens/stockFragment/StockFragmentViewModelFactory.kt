package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

class StockFragmentViewModelFactory(private val id: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockFragmentViewModel::class.java)) {
            return StockFragmentViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}