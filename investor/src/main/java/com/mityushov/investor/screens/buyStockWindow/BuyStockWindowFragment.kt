package com.mityushov.investor.screens.buyStockWindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mityushov.investor.databinding.FragmentBuyStockWindowBinding
import com.mityushov.investor.models.StockPurchase

class BuyStockWindowFragment : Fragment() {
    private val viewModel = BuyStockWindowViewModel()
    private lateinit var binding: FragmentBuyStockWindowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBuyStockWindowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buyStockBuyBtn.setOnClickListener {
            val purchase = StockPurchase(
                ticker = binding.buyStockTickerValueEt.text.toString().uppercase(),
                amount = binding.buyStockQuantityEt.text.toString().toInt(),
                // purchaseCurrency = binding.buyStockPriceEt.toString().toFloat(),
                // purchaseTax = binding.buyStockTaxEt.toString().toFloat()
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
}