package com.mityushov.investor

import android.app.Application
import androidx.work.*
import com.mityushov.investor.repository.StockRepository
import com.mityushov.investor.work.RefreshCacheWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class InvestorApplication : Application() {
    // 1) for coroutine context
    private val applicationJob = Job()
    private val applicationScope = CoroutineScope(applicationJob + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        StockRepository.init(this)
        // logging
        Timber.plant(Timber.DebugTree())
        // Work manager start
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupWork()
        }
    }

    private fun setupWork() {
        Timber.d("setup network is called")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequestBuilder<RefreshCacheWorker>(1, TimeUnit.HOURS, 5, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()


        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RefreshCacheWorker.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}