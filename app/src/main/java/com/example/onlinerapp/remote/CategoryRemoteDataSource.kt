package com.example.onlinerapp.remote

import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(
        private val categoryService: CategoryService
): BaseDataSource(){
    suspend fun getCategories() = getResult { categoryService.getAllCategories() }
}