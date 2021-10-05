package com.example.onlinerapp.ui.main.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.onlinerapp.entities.product.Product
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
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