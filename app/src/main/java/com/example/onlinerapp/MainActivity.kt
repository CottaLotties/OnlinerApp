package com.example.onlinerapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.onlinerapp.databinding.MainActivityBinding
import com.example.onlinerapp.notifications.Notifier
import com.example.onlinerapp.ui.main.cart.CartFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var requestBuilder: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scheduleNotification() // check if we need to send notifications
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        // Here we choose which menu do we need
        if (!currentFragment?.tag.equals("CART")) {
            // if we are not in the CartFragment we show the cart icon
            inflater.inflate(R.menu.menu, menu)
        }
        // else we show a delete icon
        else inflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_cart) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            if (!currentFragment?.tag.equals("CART")) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, CartFragment.newInstance(), "CART")
                        .addToBackStack(supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.javaClass?.name)
                        .commit()
            }
        }
        if (id == R.id.action_remove){
            val currentFragment: CartFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as CartFragment
            currentFragment.removeAllFromCart()
        }
        return true
    }

    private fun scheduleNotification() {
        val workManager = WorkManager.getInstance(this)
        requestBuilder = OneTimeWorkRequest.Builder(Notifier::class.java).build()
        workManager.getWorkInfoByIdLiveData(requestBuilder.id).observe(this, { workerStatus ->
            if (workerStatus != null && workerStatus.state.isFinished) {
                startNotifyWorker()
            }
        })
        workManager.beginUniqueWork("NOTIFY", ExistingWorkPolicy.REPLACE, requestBuilder).enqueue()
    }

    private fun startNotifyWorker() {
        val requestBuilder = PeriodicWorkRequest.Builder(Notifier::class.java, 30, TimeUnit.MINUTES)
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("NOTIFY", ExistingPeriodicWorkPolicy.REPLACE, requestBuilder.build())
    }
}