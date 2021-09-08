package com.example.onlinerapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.onlinerapp.Resource
import com.example.onlinerapp.entities.Product
import com.example.onlinerapp.repository.CategoryRepository

class ProductsViewModel @ViewModelInject constructor(
        private val repository: CategoryRepository
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


    //val products = repository.getProducts("ammyyummy")

//}