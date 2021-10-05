package com.example.onlinerapp.ui.main.cart

import androidx.lifecycle.ViewModel
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
        private val repository: Repository
) : ViewModel() {

    val selectedProducts = repository.getAllFromCart()

    suspend fun deleteById(id: Int){
        repository.deleteById(id)
    }

    suspend fun removeAllFromCart(){
        repository.removeAllFromCart()
    }
}

