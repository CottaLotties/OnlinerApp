package com.example.onlinerapp.notifications

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.example.onlinerapp.repository.Repository
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationManager @Inject constructor(
        private val repository: Repository){

    private val sharedPrefNotifKey = "NOTIFICATIONS"
    private val notifOff = "OFF"
    private val uniqueWorkName = "NOTIFY"
    private val notifOn = "ON"

    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mContext: Context
    private lateinit var mRequestBuilder: OneTimeWorkRequest
    private lateinit var mLifecycleOwner: LifecycleOwner

    // checking if we should send notifications
    fun checkNotificationManager(sharedPreferences: SharedPreferences, context: Context, lifecycleOwner: LifecycleOwner){
        mContext = context
        mSharedPreferences = sharedPreferences
        mLifecycleOwner = lifecycleOwner
        val tag = sharedPreferences.getString(sharedPrefNotifKey, notifOff)
        if ((getCartSize()>0)&&(tag.equals(notifOff))) scheduleNotification()
    }

    private fun getCartSize(): Int = runBlocking {
        repository.getCartSize()
    }

    private fun scheduleNotification() {
        val workManager = WorkManager.getInstance(mContext)
        mRequestBuilder = OneTimeWorkRequest.Builder(Notifier::class.java).build()
        workManager.getWorkInfoByIdLiveData(mRequestBuilder.id).observe(mLifecycleOwner, { workerStatus ->
            if (workerStatus != null && workerStatus.state.isFinished) {
                startNotifyWorker()
            }
        })
        workManager.beginUniqueWork(uniqueWorkName, ExistingWorkPolicy.REPLACE, mRequestBuilder).enqueue()
    }

    private fun startNotifyWorker() {
        // TODO: change repeatInterval; must be 4 hours
        val requestBuilder = PeriodicWorkRequest.Builder(Notifier::class.java, 30, TimeUnit.MINUTES)
        WorkManager.getInstance(mContext).enqueueUniquePeriodicWork(uniqueWorkName, ExistingPeriodicWorkPolicy.REPLACE, requestBuilder.build())
        with (mSharedPreferences.edit()) {
            putString(sharedPrefNotifKey, notifOn)
            apply()
        }
    }
}