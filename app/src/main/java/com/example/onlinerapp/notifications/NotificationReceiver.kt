package com.example.onlinerapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.core.app.NotificationManagerCompat
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// NotificationReceiver that removes everything from cart once the notification button is clicked
@AndroidEntryPoint
class NotificationReceiver: HiltBroadcastReceiver() {
    @Inject
    lateinit var repository: Repository
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        NotificationManagerCompat.from(context).cancelAll()
        removeAllFromCart()
    }

    private fun removeAllFromCart() = runBlocking {
        launch {
            repository.removeAllFromCart()
        }
    }
}

// Wrapper class to solve the problem with broadcastReceiver injection
abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {}
}
