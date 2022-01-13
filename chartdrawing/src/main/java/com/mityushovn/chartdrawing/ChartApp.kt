package com.mityushovn.chartdrawing

import android.app.Application
import timber.log.Timber

class ChartApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // logging
        Timber.plant(Timber.DebugTree())
    }
}