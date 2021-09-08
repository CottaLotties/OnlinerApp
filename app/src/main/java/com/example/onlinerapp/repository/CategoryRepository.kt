package com.example.onlinerapp.repository

import com.example.onlinerapp.database.CategoryDao
import com.example.onlinerapp.performGetOperation
import com.example.onlinerapp.remote.CategoryRemoteDataSource
import javax.inject.Inject

// a repository class for the main (category list) screen
class CategoryRepository @Inject constructor(
        private val categoryRemoteDataSource: CategoryRemoteDataSource,
        private val categoryLocalDataSource: CategoryDao
){
    fun getCategories() = performGetOperation(
            databaseQuery = { categoryLocalDataSource.getAllCategories() },
            networkCall = { categoryRemoteDataSource.getCategories() },
            saveCallResult = { categoryLocalDataSource.insertAllCategories(it.schemas) }
    )

    fun getProducts() = performGetOperation(
        databaseQuery = { categoryLocalDataSource.getAllProducts() },
        networkCall = { categoryRemoteDataSource.getProducts() },
        saveCallResult = { categoryLocalDataSource.insertAllProducts(it.products) }
    )

}