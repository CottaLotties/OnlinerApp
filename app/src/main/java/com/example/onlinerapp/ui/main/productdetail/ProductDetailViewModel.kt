package com.example.onlinerapp.ui.main.productdetail

import androidx.lifecycle.*
import com.example.onlinerapp.entities.product.Product
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun addToCart(product: Product){
        viewModelScope.launch {
            repository.addProductToCart(product)
        }
    }
}