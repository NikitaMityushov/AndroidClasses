package com.mityushov.investor.screens.stockFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mityushov.investor.databinding.FragmentStockBinding
import com.mityushov.investor.models.StockAPI
import java.util.*

private const val TAG = "StockFragment"
private const val ARG_STOCK_ID = "stock_id"

class StockFragment private constructor() : Fragment() {
    private lateinit var binding: FragmentStockBinding
    private val detailViewModel: StockFragmentViewModel = StockFragmentViewModel()
    private lateinit var id: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stockId: UUID = arguments?.getSerializable(ARG_STOCK_ID) as UUID
        id = stockId
        Log.d(TAG, "StockFragments args bundle stock ID: $stockId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(detailViewModel.getData(id))
    }

    private fun updateUI(stock: StockAPI) {
        binding.apply {
            fragmentStockTickerValueTv.text = stock.getTicker()
            fragmentStockAmountValueTv.text = stock.getAmount().toString()
            fragmentStockPurchaseCurrencyValueTv.text = String.format("%.2f", stock.getPurchaseCurrency())
            fragmentStockPurchasePriceValueTv.text = String.format("%.2f", stock.getPurchasePrice())
            fragmentStockPurchaseTaxValueTv.text = String.format("%.2f", stock.getPurchaseTax())
            fragmentStockCurrentCurrencyValueTv.text = String.format("%.2f", stock.getCurrentCurrency())
            fragmentStockCurrentPriceValueTv.text = String.format("%.2f", stock.getCurrentPrice())
            fragmentStockTotalProfitValueTv.text = String.format("%.2f", stock.getTotalProfit())
            fragmentStockProfitabilityValueTv.text = String.format("%.2f", stock.getProfitability())
        }
    }

    companion object {
        fun newInstance(stockId: UUID): StockFragment {
            Log.d(TAG, "newInstance is called")
            val args = Bundle().apply {
                putSerializable(ARG_STOCK_ID, stockId)
            }

            return StockFragment().apply {
                arguments = args
            }
        }
    }

}