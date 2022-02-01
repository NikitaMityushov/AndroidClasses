package com.mityushov.investor.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mityushov.investor.R
import com.mityushov.investor.data.StockRepository
import com.mityushov.investor.utils.sendNotification
import timber.log.Timber
import java.lang.Exception

class RefreshCacheWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    private val repository = StockRepository.get()

    companion object {
        const val WORKER_NAME = "RefreshCacheWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            // 1) main work
            repository.refresh()
            // 2) send notification about works completion
            sendNotification()
            // 3) result success
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun sendNotification() {
        // 1) create channel
        createChannel(
            applicationContext.getString(R.string.investor_notification_channel_id),
            applicationContext.getString(R.string.app_name)
        )
        // 2) send notification, using an extension function of NotificationManager
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification("Investors data is updated", applicationContext)
        Timber.d("Work of investor is completed")
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Investors data is updated"

            val notificationManager = applicationContext.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}