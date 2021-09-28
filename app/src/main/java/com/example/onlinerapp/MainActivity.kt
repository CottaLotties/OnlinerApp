package com.example.onlinerapp

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinerapp.databinding.MainActivityBinding
import com.example.onlinerapp.notifications.NotificationManager
import com.example.onlinerapp.ui.main.cart.CartFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check if we have to send notifications
        notificationManager.checkNotificationManager(this.getPreferences(Context.MODE_PRIVATE) ?: return, this, this)

        // check if the app was opened by a notification click (with a special action)
        val intent = intent
        val action = intent.getStringExtra("FRAGMENT")
        if (action != null) {
            if (action == "CART") {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, CartFragment.newInstance(), "CART")
                        .addToBackStack(supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.javaClass?.name)
                        .commit()
            }
        }
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
}