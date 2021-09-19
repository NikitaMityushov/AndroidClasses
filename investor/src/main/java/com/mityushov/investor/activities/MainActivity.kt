package com.mityushov.investor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mityushov.investor.R
import com.mityushov.investor.screens.buyStockWindow.BuyStockWindowFragment
import com.mityushov.investor.screens.stockFragment.StockFragment
import com.mityushov.investor.screens.stockFragmentList.StockListFragment
import java.util.*

private const val TAG: String = "MainActivity"

class MainActivity : AppCompatActivity(), StockListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = StockListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onStockSelected(stockId: UUID) {
        Log.d(TAG, "onStockedSelected() is called. StockId is $stockId")
        val fragment = StockFragment.newInstance(stockId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBuyButtonPressed() {
        val fragment = BuyStockWindowFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}