package com.mityushov.investor.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mityushov.investor.repository.StockRepository
import java.lang.Exception

class RefreshCacheWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    private val repository = StockRepository.get()

    companion object {
        const val WORKER_NAME = "RefreshCacheWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            repository.refresh()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}