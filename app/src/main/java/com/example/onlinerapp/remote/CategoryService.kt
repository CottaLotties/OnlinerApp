package com.example.onlinerapp.remote

import com.example.onlinerapp.entities.CategoryList
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET("schemas?limit=30&page=1")
    suspend fun getAllCategories() : Response<CategoryList>
}