package com.example.onlinerapp.repository

import com.example.onlinerapp.database.CategoryDao
import com.example.onlinerapp.entities.Product
import com.example.onlinerapp.performGetOperation
import com.example.onlinerapp.remote.RemoteDataSource
import javax.inject.Inject

// a repository class for the main (category list) screen
class Repository @Inject constructor(
        private val categoryRemoteDataSource: RemoteDataSource,
        private val categoryLocalDataSource: CategoryDao
){
    fun getCategories() = performGetOperation(
            databaseQuery = { categoryLocalDataSource.getAllCategories() },
            networkCall = { categoryRemoteDataSource.getCategories() },
            saveCallResult = { categoryLocalDataSource.insertAllCategories(it.schemas) }
    )

    fun getProducts(key: String) = performGetOperation(
        databaseQuery = { categoryLocalDataSource.getProducts("%$key%") },
        networkCall = { categoryRemoteDataSource.getProducts(key) },
        saveCallResult = { categoryLocalDataSource.insertAllProducts(it.products) }
    )

    fun getProduct(key: String) = categoryLocalDataSource.getProduct(key)

    suspend fun addProductToCart(product: Product) = categoryLocalDataSource.addToCart(product)

    fun getAllFromCart() = categoryLocalDataSource.getAllSelectedProducts()

    suspend fun deleteById(id: Int) = categoryLocalDataSource.deleteById(id)
}