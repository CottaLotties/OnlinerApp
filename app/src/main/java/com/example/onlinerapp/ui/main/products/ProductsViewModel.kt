package com.example.onlinerapp.ui.main.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.onlinerapp.Resource
import com.example.onlinerapp.entities.product.Product
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
        private val repository: Repository
) : ViewModel() {

    private val _key = MutableLiveData<String>()

    private val _products = _key.switchMap { key ->
        repository.getProducts(key)
    }

    val products: LiveData<Resource<List<Product>>> = _products

    fun start(key: String) {
        _key.value = key
    }
}