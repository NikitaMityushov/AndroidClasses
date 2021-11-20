package com.mityushovn.imageviewerrestapigooglecourse

import android.app.Application
import com.mityushovn.imageviewerrestapigooglecourse.network.MarsApiService
import com.mityushovn.imageviewerrestapigooglecourse.network.Repository
import timber.log.Timber

class MarsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        // repository initialize
        Repository.init(this)
        // logging
        Timber.plant(Timber.DebugTree())
    }

}