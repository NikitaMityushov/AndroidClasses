package com.mityushov.investor.screens.stockFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mityushov.investor.data.StockRepository
import com.mityushov.investor.databinding.FragmentStockBinding
import com.mityushov.investor.navigation.navigator
import com.mityushov.investor.models.CacheStockPurchase
import java.util.*
import com.mityushov.investor.utils.setTextColorRedOrGreen
import timber.log.Timber

private const val ARG_STOCK_ID = "stock_id"

class StockFragment private constructor() : Fragment() {
    private lateinit var binding: FragmentStockBinding
    private lateinit var viewModel: StockFragmentViewModel
    private lateinit var viewModelFactory: StockFragmentViewModelFactory
    private lateinit var id: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stockId: UUID = arguments?.getSerializable(ARG_STOCK_ID) as UUID
        id = stockId

        viewModelFactory = StockFragmentViewModelFactory(id, StockRepository.get())

        Timber.i("StockFragments args bundle stock ID: $stockId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStockBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory).get(StockFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, { stock ->
            updateUI(stock)
        })

        binding.fragmentStockDeleteBtn.setOnClickListener {
            viewModel.deleteStock()
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
            this.activity?.onBackPressed()
        }

        binding.fragmentStockUpdateBtn.setOnClickListener {
            Timber.d("Update button is pressed")
            this.navigator().onUpdateButtonPressed(viewModel.getStockPurchase())
        }
    }

    private fun updateUI(stock: CacheStockPurchase) {
        Timber.d("UpdateUI is called, stock is $stock")
            binding.apply {
                fragmentStockTickerValueTv.text = stock.ticker
                fragmentStockAmountValueTv.text = stock.amount.toString()
                fragmentStockPurchaseCurrencyValueTv.text = String.format("%.2f", stock.purchaseCurrency)
                fragmentStockPurchasePriceValueTv.text = String.format("%.2f", stock.purchasePrice)
                fragmentStockPurchaseTaxValueTv.text = String.format("%.2f", stock.purchaseTax)
                fragmentStockCurrentCurrencyValueTv.text = String.format("%.2f", stock.currentCurrency)
                fragmentStockCurrentPriceValueTv.text = String.format("%.2f", stock.currentPrice)
                fragmentStockTotalProfitValueTv.apply {
                    val value = stock.totalProfit
                    text = String.format("%.2f", value)
                    setTextColorRedOrGreen(value, this)
                }
                fragmentStockProfitabilityValueTv.apply {
                    val value = stock.profitability
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