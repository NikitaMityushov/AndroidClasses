package com.mityushov.investor.screens.buyStockWindow

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.mityushov.investor.databinding.FragmentBuyStockWindowBinding
import com.mityushov.investor.models.StockPurchase
import timber.log.Timber

class BuyStockWindowFragment : Fragment() {
    private lateinit var viewModel: BuyStockWindowViewModel
    private lateinit var binding: FragmentBuyStockWindowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBuyStockWindowBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(BuyStockWindowViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buyStockTickerValueEt.setOnKeyListener { _, _, _ ->
                if (isEditTextValid(buyStockTickerValueEt.text)) {
                    // Clear the error.
                    buyStockTickerValueEt.error = null
                }
                false
            }

            buyStockQuantityEt.setOnKeyListener { _, _, _ ->
                if (isEditTextValid(buyStockQuantityEt.text)) {
                    // Clear the error.
                    buyStockQuantityEt.error = null
                }
                false
            }

            buyStockPriceEt.setOnKeyListener { _, _, _ ->
                if (isEditTextValid(buyStockPriceEt.text)) {
                    // Clear the error.
                    buyStockPriceEt.error = null
                }
                false
            }

            buyStockTaxEt.setOnKeyListener { _, _, _ ->
                if (isEditTextValid(buyStockTaxEt.text)) {
                    // Clear the error.
                    buyStockTaxEt.error = null
                }
                false
            }

        }

        binding.buyStockBuyBtn.setOnClickListener {

            Timber.d("Ticker is ${binding.buyStockTickerValueEt.text.toString().uppercase()}")
            Timber.d("amount is ${binding.buyStockQuantityEt.text}")
            Timber.d("PurchaseCurrency is ${binding.buyStockPriceEt.text}")
            Timber.d("PurchaseTax is ${binding.buyStockTaxEt.text}")

            // check if EditText views content is valid
            if (!isEditTextValid(binding.buyStockTickerValueEt.text)) {
                binding.buyStockTickerValueEt.error = "The field should be not empty!"
                return@setOnClickListener
            } else {
                binding.buyStockTickerValueEt.error
            }

            if (!isEditTextValid(binding.buyStockQuantityEt.text)) {
                binding.buyStockQuantityEt.error = "The field should be not empty!"
                return@setOnClickListener
            } else {
                binding.buyStockQuantityEt.error
            }

            if (!isEditTextValid(binding.buyStockPriceEt.text)) {
                binding.buyStockPriceEt.error = "The field should be not empty!"
                return@setOnClickListener
            } else {
                binding.buyStockPriceEt.error
            }

            if (!isEditTextValid(binding.buyStockTaxEt.text)) {
                binding.buyStockTaxEt.error = "The field should be not empty!"
                return@setOnClickListener
            } else {
                binding.buyStockTaxEt.error
            }

            // create a new purchase and add it to database
            val purchase = StockPurchase(
                ticker = binding.buyStockTickerValueEt.text.toString().uppercase(),
                amount = binding.buyStockQuantityEt.text.toString().toInt(),
                purchaseCurrency = binding.buyStockPriceEt.text.toString().toFloat(),
                purchaseTax = binding.buyStockTaxEt.text.toString().toFloat()
            )

            val result = viewModel.addStock(purchase)

            if (result) {
                Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()
                this.activity?.onBackPressed()
            } else {
                Toast.makeText(context, "This ticker doesn't exists!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEditTextValid(text: Editable?): Boolean {
        return text != null && text.isNotEmpty()
    }
}