package com.mityushov.investor.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomappbar.BottomAppBar
import com.mityushov.investor.R
import com.mityushov.investor.databinding.ActivityMainBinding
import com.mityushov.investor.navigation.Navigator
import com.mityushov.investor.models.StockPurchase
import com.mityushov.investor.network.NetworkStatus
import com.mityushov.investor.screens.aboutFragment.AboutFragment
import com.mityushov.investor.screens.buyStockWindow.BuyStockWindowFragment
import com.mityushov.investor.screens.stockFragment.StockFragment
import com.mityushov.investor.screens.stockFragmentList.StockListFragment
import com.mityushov.investor.screens.updateStockWindow.UpdateStockWindowFragment
import com.mityushov.investor.utils.disableViewDuringAnimation
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    lateinit var bottomAppBar: BottomAppBar

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUI()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1) init block
        binding = ActivityMainBinding.inflate(this.layoutInflater).also {
            setContentView(it.root)
        }
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        bottomAppBar = binding.bottomAppBar
        setSupportActionBar(bottomAppBar)

        // apply night mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        // 2) Start screen init
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, StockListFragment())
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        // 3) Handle the FAB click
        binding.refreshBtn.setOnClickListener {
            Timber.d("refresh is clicked")
            viewModel.refreshScreen()
            // animations, rolling
            scaleAndRotateView(it)
        }
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

//    private fun rotateViewAnimation(view: View, repeatCount: Int = 1, duration: Long = 800) {
//        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f)
//        animator.disableViewDuringAnimation(view)
//        animator.repeatCount = repeatCount
//        animator.repeatMode = ObjectAnimator.REVERSE
//        animator.duration = duration
//        animator.start()
//    }

    private fun scaleAndRotateView(view: View) {
        // 1) create scale animator
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .8f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .8f)
        val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
        scaleAnimator.disableViewDuringAnimation(view)
        scaleAnimator.duration = 100
        scaleAnimator.repeatCount = 1
        scaleAnimator.repeatMode = ObjectAnimator.REVERSE

        // 2) create rotate animator
        val rotateAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f)
        rotateAnimator.duration = 3000
        rotateAnimator.disableViewDuringAnimation(view)
        rotateAnimator.repeatCount = 11
        rotateAnimator.repeatMode = ObjectAnimator.RESTART

        // 3) create AnimationSet

        val set = AnimatorSet()
        set.playTogether(scaleAnimator, rotateAnimator)

        // 4) start
//        set.start()
// рабочая система, но состояние анимации остается на момент остановки, не начальным, надо переделать в будещем
        viewModel.status.observe(this, {value ->
            when (value) {
                NetworkStatus.LOADING -> set.start()
                NetworkStatus.DONE -> set.cancel()
                else -> {
                    set.cancel()
                }
            }
        })

    }
}