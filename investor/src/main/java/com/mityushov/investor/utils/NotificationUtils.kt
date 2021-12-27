package com.mityushov.investor.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.mityushov.investor.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // 1)
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.investor_notification_channel_id)
    )

    // 2)
    builder
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(applicationContext.getString(R.string.app_name))
        .setContentText(messageBody)

    // 3)
    notify(NOTIFICATION_ID, builder.build())
}