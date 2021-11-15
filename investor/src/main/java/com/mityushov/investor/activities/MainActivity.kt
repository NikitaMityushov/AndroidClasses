package com.mityushov.investor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUI()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater).also {
            setContentView(it.root)
        }
        // Home screen init
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, StockListFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        updateUI()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.overflow_menu_about_fragment -> {
                this.onAboutButtonPressed()
                true
            }

            R.id.overflow_menu_add_stock -> {
                this.onBuyButtonPressed()
                true
            }

            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
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

    private fun updateUI() {
        // 1) Init home button
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }
}