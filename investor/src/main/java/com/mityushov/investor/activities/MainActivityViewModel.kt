package com.mityushov.investor.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushov.investor.repository.StockRepository
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    private val repository = StockRepository.get()

    val status = repository.status

    fun refreshScreen() {
        viewModelScope.launch {
            repository.refresh()
        }
    }

    fun getItemsCount(): Int {
        return repository.list.value?.size ?: 0
    }
}