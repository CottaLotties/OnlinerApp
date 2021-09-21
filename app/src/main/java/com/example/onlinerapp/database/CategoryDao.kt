package com.example.onlinerapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.onlinerapp.entities.Cart
import com.example.onlinerapp.entities.Category
import com.example.onlinerapp.entities.Product

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories() : LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<Category>)

    // a select query to get all the products of the same category; html_url contains the category key
    @Query("SELECT * FROM products WHERE html_url LIKE :key")
    fun getProducts(key: String) : LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<Product>)

    @Query("SELECT * FROM products WHERE [key] = :key")
    fun getProduct(key: String): LiveData<Product>

    @Query("SELECT * FROM cart")
    fun getAllSelectedProducts() : LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Cart::class)
    suspend fun addToCart(product: Product)

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteById(id: Int)
}