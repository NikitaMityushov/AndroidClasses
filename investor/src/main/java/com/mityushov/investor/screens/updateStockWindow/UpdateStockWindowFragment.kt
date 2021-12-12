package com.mityushov.investor.screens.updateStockWindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mityushov.investor.databinding.FragmentUpdateStockWindowBinding
import com.mityushov.investor.models.StockPurchase
import timber.log.Timber

private const val ARG_STOCK_PURCHASE = "stock_purchase"

class UpdateStockWindowFragment private constructor() : Fragment() {
    private lateinit var binding: FragmentUpdateStockWindowBinding
    private lateinit var viewModel: UpdateStockWindowViewModel
    private lateinit var viewModelFactory: UpdateStockWindowViewModelFactory
    private lateinit var stockPurchase: StockPurchase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stock: StockPurchase = arguments?.getSerializable(ARG_STOCK_PURCHASE) as StockPurchase
        stockPurchase = stock

        viewModelFactory = UpdateStockWindowViewModelFactory(stockPurchase)

        Timber.i("UpdateStockWindowFragment args bundle stock ticker: ${stockPurchase.ticker}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUpdateStockWindowBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UpdateStockWindowViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialUpdateUI()
        binding.updateStockSaveChangesBtn.setOnClickListener {
            updateStockPurchaseFromUI()
            viewModel.updateStockPurchase()
            Timber.d("Stock is $stockPurchase")
            Timber.d("Save changes button is pressed")
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show()
            this.activity?.onBackPressed()
        }

    }

    private fun initialUpdateUI() {
        with(binding) {
            updateStockTickerValueEt.setText(stockPurchase.ticker)
            updateStockQuantityEt.setText(stockPurchase.amount.toString())
            updateStockPriceEt.setText(stockPurchase.purchaseCurrency.toString())
            updateStockTaxEt.setText(stockPurchase.purchaseTax.toString())
        }
    }

    private fun updateStockPurchaseFromUI() {
        with(binding) {
            stockPurchase.amount = updateStockQuantityEt.text.toString().toInt()
            stockPurchase.purchaseCurrency = updateStockPriceEt.text.toString().toFloat()
            stockPurchase.purchaseTax = updateStockTaxEt.text.toString().toFloat()
        }
    }

    companion object {
        fun newInstance(stockPurchase: StockPurchase): UpdateStockWindowFragment {
            Timber.i("newInstance is called")
            val args = Bundle().apply {
                putSerializable(ARG_STOCK_PURCHASE, stockPurchase)
            }

            return UpdateStockWindowFragment().apply {
                arguments = args
            }
        }
    }

}