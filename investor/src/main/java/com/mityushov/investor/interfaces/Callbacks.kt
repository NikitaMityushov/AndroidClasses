package com.mityushov.investor.interfaces

import com.mityushov.investor.models.StockPurchase
import java.util.*

/**
 * Interface for every change fragment in the MainActivity
 */

interface Callbacks {
    fun onStockSelected(stockId: UUID)
    fun onBuyButtonPressed()
    fun onUpdateButtonPressed(stockPurchase: StockPurchase)
}