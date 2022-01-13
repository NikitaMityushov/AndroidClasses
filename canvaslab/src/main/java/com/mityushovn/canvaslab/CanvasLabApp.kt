package com.mityushovn.canvaslab

import android.app.Application
import timber.log.Timber

class CanvasLabApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // logging
        Timber.plant(Timber.DebugTree())
    }
}