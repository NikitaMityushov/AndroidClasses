package com.mityushov.investor.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mityushov.investor.data.IStockRepository

class MainActViewModelFactory(private val repository: IStockRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}