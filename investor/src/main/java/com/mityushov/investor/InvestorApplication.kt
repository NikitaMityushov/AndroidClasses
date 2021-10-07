package com.mityushov.investor

import android.app.Application
import com.mityushov.investor.database.StockDatabase
import com.mityushov.investor.database.StockRepository
import timber.log.Timber

class InvestorApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // database
        val db by lazy {
        }
        StockRepository.init(this)
        // logging
        Timber.plant(Timber.DebugTree())
    }
}