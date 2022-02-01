package com.mityushov.investor.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushov.investor.data.IStockRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: IStockRepository): ViewModel() {
    val status = repository.status

    fun refreshScreen() {
        viewModelScope.launch {
            repository.refresh()
        }
    }
}