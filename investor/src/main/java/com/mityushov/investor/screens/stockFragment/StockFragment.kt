package com.mityushov.investor.screens.stockFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mityushov.investor.databinding.FragmentStockBinding
import com.mityushov.investor.models.StockAPI
import java.util.*
import com.mityushov.investor.utils.setTextColorRedOrGreen
import timber.log.Timber

private const val ARG_STOCK_ID = "stock_id"

class StockFragment private constructor() : Fragment() {
    private lateinit var binding: FragmentStockBinding
    private lateinit var detailViewModel: StockFragmentViewModel
    private lateinit var viewModelFactory: StockFragmentViewModelFactory
    private lateinit var id: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stockId: UUID = arguments?.getSerializable(ARG_STOCK_ID) as UUID
        id = stockId

        viewModelFactory = StockFragmentViewModelFactory(id)

        Timber.i("StockFragments args bundle stock ID: $stockId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStockBinding.inflate(inflater, container, false)

        detailViewModel = ViewModelProvider(this, viewModelFactory).get(StockFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.data.observe(viewLifecycleOwner, {stock ->
            updateUI(stock)
        })
    
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
                fragmentStockTotalProfitValueTv.apply {
                    val value = stock.getTotalProfit()
                    text = String.format("%.2f", value)
                    setTextColorRedOrGreen(value, this)
                }
                fragmentStockProfitabilityValueTv.apply {
                    val value = stock.getProfitability()
                    text = String.format("%.2f%%", value)
                    setTextColorRedOrGreen(value, this)
                }
            }
    }

    companion object {
        fun newInstance(stockId: UUID): StockFragment {
            Timber.i("newInstance is called")
            val args = Bundle().apply {
                putSerializable(ARG_STOCK_ID, stockId)
            }

            return StockFragment().apply {
                arguments = args
            }
        }
    }

}