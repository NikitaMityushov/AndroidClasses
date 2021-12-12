package com.mityushov.investor.navigation

import androidx.fragment.app.Fragment
import com.mityushov.investor.models.StockPurchase
import java.util.*
/**
 * Interface for every change fragment in the MainActivity
 */
interface Navigator {
    fun onStockSelected(stockId: UUID)
    fun onBuyButtonPressed()
    fun onUpdateButtonPressed(stockPurchase: StockPurchase)
    fun onAboutButtonPressed()
}

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}