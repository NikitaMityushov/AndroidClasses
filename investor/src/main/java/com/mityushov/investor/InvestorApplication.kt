package com.mityushov.investor

import android.app.Application
import com.mityushov.investor.database.StockDatabase
import com.mityushov.investor.database.StockRepository

class InvestorApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val db by lazy {
        }
        StockRepository.init(this)
    }
}