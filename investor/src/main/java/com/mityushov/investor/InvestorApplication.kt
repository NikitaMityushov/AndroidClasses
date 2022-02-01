package com.mityushov.investor

import android.app.Application
import androidx.room.Room
import androidx.work.*
import com.mityushov.investor.data.StockRepository
import com.mityushov.investor.data.local.LocalCacheStockDataSource
import com.mityushov.investor.data.local.LocalStockDataSource
import com.mityushov.investor.data.remote.RemoteStockDataSource
import com.mityushov.investor.database.StockDatabase
import com.mityushov.investor.work.RefreshCacheWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val DATABASE_NAME = "stocks"

class InvestorApplication : Application() {
    // 1) for coroutine context
    private val applicationJob = Job()
    private val applicationScope = CoroutineScope(applicationJob + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        // init database
        val database: StockDatabase by lazy {
            Room.databaseBuilder(
                this.applicationContext,
                StockDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }
        // init Repository
        StockRepository.init(
            localStockDataSource = LocalStockDataSource(database.stockDao()),
            localCacheStockDataSource = LocalCacheStockDataSource(database.cacheStockDao()),
            remoteStockDataSource = RemoteStockDataSource()
        )
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