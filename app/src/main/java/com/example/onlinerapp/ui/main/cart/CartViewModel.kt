package com.example.onlinerapp.ui.main.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onlinerapp.repository.Repository

class CartViewModel @ViewModelInject constructor(
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

