package com.example.onlinerapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onlinerapp.repository.CategoryRepository

class ProductsViewModel @ViewModelInject constructor(
        private val repository: CategoryRepository
) : ViewModel() {
    val products = repository.getProducts()
}