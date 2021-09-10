package com.mityushov.investor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mityushov.investor.UI.StockListFragment

private const val TAG: String = "MainActivity"

/* 11.09.21 tasks:
    1) view models for each fragment
 */

class MainActivity : AppCompatActivity() {
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
}