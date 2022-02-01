package com.mityushov.investor.screens.stockFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mityushov.investor.data.IStockRepository
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.models.asStockPurchase
import com.mityushov.investor.models.CacheStockPurchase
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.*

class StockFragmentViewModel(
    private val id: UUID,
    private val repository: IStockRepository
) : ViewModel() {

    // TODO: 01.02.2022 need thinking
    val data: LiveData<CacheStockPurchase> = getDataFromRepository(id)

    fun deleteStock() = runBlocking {
        repository.deleteStockPurchaseFromId(id)
    }

    fun getStockPurchase(): StockPurchase {
        Timber.d("StockFragmentViewModel getStockPurchase is called")
        return data.value.asStockPurchase()
    }

    private fun getDataFromRepository(id: UUID) = runBlocking {
        return@runBlocking repository.getCacheStockPurchaseFromId(id)
    }

}
