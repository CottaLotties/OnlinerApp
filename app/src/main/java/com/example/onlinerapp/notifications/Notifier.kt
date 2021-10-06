package com.example.onlinerapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.onlinerapp.MainActivity
import com.example.onlinerapp.R

class Notifier(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val mContext = context
    private val channelId = "onlinerApp_nc"
    private val notificationChannelName = "onlinerApp_channel_1"
    private val fragmentTag = "FRAGMENT"
    private val cartFragmentTag = "CART"

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // intents that are used when the notification itself is clicked
        val resultIntent = Intent(applicationContext, MainActivity::class.java).putExtra(
            fragmentTag,
            cartFragmentTag
        )
        val resultPendingIntent = PendingIntent.getActivity(
            applicationContext, 0, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // intents that are used once the remove button is clicked
        val removeActionIntent = Intent(applicationContext, NotificationReceiver::class.java)
        val actionPendingIntent = PendingIntent
                .getBroadcast(
                    applicationContext,
                    1,
                    removeActionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

        val notification = NotificationCompat.Builder(mContext, channelId)
            .addAction(R.drawable.outline_delete_white_24, mContext.resources.getString(R.string.notification_action), actionPendingIntent)
            .setContentTitle(mContext.resources.getString(R.string.notification_title))
            .setContentText(mContext.resources.getString(R.string.notification_message))
            .setSmallIcon(R.drawable.ic_baseline_shopping_cart_24)
            .setContentIntent(resultPendingIntent)
        notificationManager.notify(1, notification.build())
    }
}