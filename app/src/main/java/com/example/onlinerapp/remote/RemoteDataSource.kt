package com.example.onlinerapp.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val categoryService: RemoteService
): BaseDataSource(){
    suspend fun getCategories() = getResult { categoryService.getAllCategories() }
    suspend fun getProducts(key: String) = getResult { categoryService.getProducts(key) }
}