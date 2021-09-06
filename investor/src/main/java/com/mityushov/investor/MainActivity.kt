package com.mityushov.investor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

private const val TAG: String = "MainActivity"

/* 7.09.21 tasks:
    1) fragment_buy_stock_window
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}