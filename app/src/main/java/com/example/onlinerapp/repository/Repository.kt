package com.example.onlinerapp.repository

import androidx.lifecycle.LiveData
import com.example.onlinerapp.Resource
import com.example.onlinerapp.database.CategoryDao
import com.example.onlinerapp.entities.categories.Category
import com.example.onlinerapp.entities.product.Product
import com.example.onlinerapp.performGetOperation
import com.example.onlinerapp.remote.RemoteDataSource
import javax.inject.Inject

// a repository class for the main (category list) screen
class Repository @Inject constructor(
        private val categoryRemoteDataSource: RemoteDataSource,
        private val categoryLocalDataSource: CategoryDao
){
    fun getCategories(): LiveData<Resource<List<Category>>> = performGetOperation(
            databaseQuery = { categoryLocalDataSource.getAllCategories() },
            networkCall = { categoryRemoteDataSource.getCategories() },
            saveCallResult = { categoryLocalDataSource.insertAllCategories(it.schemas) }
    )

    fun getProducts(key: String): LiveData<Resource<List<Product>>> = performGetOperation(
        databaseQuery = { categoryLocalDataSource.getProducts("%$key%") },
        networkCall = { categoryRemoteDataSource.getProducts(key) },
        saveCallResult = { categoryLocalDataSource.insertAllProducts(it.products) }
    )

    fun getProduct(key: String): LiveData<Product> = categoryLocalDataSource.getProduct(key)

    suspend fun addProductToCart(product: Product) = categoryLocalDataSource.addToCart(product)

    fun getAllFromCart(): LiveData<List<Product>> = categoryLocalDataSource.getAllSelectedProducts()

    suspend fun deleteById(id: Int) = categoryLocalDataSource.deleteById(id)

    suspend fun removeAllFromCart() = categoryLocalDataSource.removeAllFromCart()

    suspend fun getCartSize(): Int = categoryLocalDataSource.getCartSize()

    suspend fun inCart(key: String):Boolean = categoryLocalDataSource.inCart(key)
}