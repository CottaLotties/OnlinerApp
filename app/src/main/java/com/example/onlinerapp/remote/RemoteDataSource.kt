package com.example.onlinerapp.remote

import com.example.onlinerapp.Resource
import com.example.onlinerapp.entities.categories.CategoryList
import com.example.onlinerapp.entities.product.ProductList
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val categoryService: OnlinerAPI
){
    suspend fun getCategories(): Resource<CategoryList> = getResult { categoryService.getAllCategories() }
    suspend fun getProducts(key: String): Resource<ProductList> = getResult { categoryService.getProducts(key) }

    private suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a reason: $message")
    }
}