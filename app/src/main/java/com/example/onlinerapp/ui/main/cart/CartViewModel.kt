package com.example.onlinerapp.ui.main.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinerapp.entities.product.Product
import com.example.onlinerapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
        private val repository: Repository
) : ViewModel() {

    val selectedProducts: LiveData<List<Product>> = repository.getAllFromCart()

    fun deleteById(id: Int){
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    fun removeAllFromCart(){
        viewModelScope.launch {
            repository.removeAllFromCart()
        }
    }
}

