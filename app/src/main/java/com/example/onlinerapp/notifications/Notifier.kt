package com.example.onlinerapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.onlinerapp.R

class Notifier(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val mContext = context
    private val channelId = "onlinerApp_nc"
    private val notificationChannelName = "onlinerApp_channel_1"

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, notificationChannelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(mContext, channelId)
            .setContentTitle("Ваши товары грустят")
            .setContentText("Пожалуйста, купите их:)")
            .setSmallIcon(R.drawable.ic_baseline_shopping_cart_24)

        notificationManager.notify(1, notification.build())
    }
}