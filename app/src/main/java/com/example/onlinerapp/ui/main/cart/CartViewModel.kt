package com.example.onlinerapp.ui.main.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onlinerapp.repository.Repository

class CartViewModel @ViewModelInject constructor(
        repository: Repository
) : ViewModel() {
    val selectedProducts = repository.getAllFromCart()
}