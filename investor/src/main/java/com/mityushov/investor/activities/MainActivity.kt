package com.mityushov.investor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mityushov.investor.R
import com.mityushov.investor.databinding.ActivityMainBinding
import com.mityushov.investor.interfaces.Navigator
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.screens.aboutFragment.AboutFragment
import com.mityushov.investor.screens.buyStockWindow.BuyStockWindowFragment
import com.mityushov.investor.screens.stockFragment.StockFragment
import com.mityushov.investor.screens.stockFragmentList.StockListFragment
import com.mityushov.investor.screens.updateStockWindow.UpdateStockWindowFragment
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater).also { setContentView(it.root) }
        // setSupportActionBar(binding.toolbar)

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
        Timber.d("onStockedSelected() is called. StockId is $stockId")
        val fragment = StockFragment.newInstance(stockId)
        launchFragment(fragment)
    }

    override fun onBuyButtonPressed() {
        val fragment = BuyStockWindowFragment()
        launchFragment(fragment)
    }

    override fun onUpdateButtonPressed(stockPurchase: StockPurchase) {
        Timber.d("onUpdateButtonPressed() is called. Stock ticker is ${stockPurchase.ticker}")
        val fragment = UpdateStockWindowFragment.newInstance(stockPurchase)
        launchFragment(fragment)
    }

    override fun onAboutButtonPressed() {
        val fragment = AboutFragment()
        launchFragment(fragment)
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}