package com.example.onlinerapp.remote

import com.example.onlinerapp.entities.CategoryList
import com.example.onlinerapp.entities.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {
    @GET("schemas?limit=30&page=1")
    suspend fun getAllCategories() : Response<CategoryList>

    @GET("search/{key}?limit=30&page=1")
    suspend fun getProducts(@Path("key") key: String) : Response<ProductList>
}