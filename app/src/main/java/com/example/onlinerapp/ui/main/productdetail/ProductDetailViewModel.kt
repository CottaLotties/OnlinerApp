package com.example.onlinerapp.ui.main.productdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.onlinerapp.entities.Product
import com.example.onlinerapp.repository.Repository

class ProductDetailViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _key = MutableLiveData<String>()

    private val _product = _key.switchMap { key ->
        repository.getProduct(key)
    }
    val product: LiveData<Product> = _product

    fun start(key: String) {
        _key.value = key
    }

    suspend fun addToCart(product: Product){
        repository.addProductToCart(product)
    }
}