package com.example.onlinerapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlinerapp.entities.cart.Cart
import com.example.onlinerapp.entities.categories.Category
import com.example.onlinerapp.entities.product.Product

@Dao
interface CategoryDao {
    // queries for categories table
    @Query("SELECT * FROM categories")
    fun getAllCategories() : LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<Category>)

    // queries for products table
    // a select query to get all the products of the same category; html_url contains the category key
    @Query("SELECT * FROM products WHERE html_url LIKE :key")
    fun getProducts(key: String) : LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<Product>)

    @Query("SELECT * FROM products WHERE [key] = :key")
    fun getProduct(key: String): LiveData<Product>

    // queries for cart table
    @Query("SELECT * FROM cart")
    fun getAllSelectedProducts() : LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = Cart::class)
    suspend fun addToCart(product: Product)

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM cart")
    suspend fun removeAllFromCart()

    @Query("SELECT COUNT(id) FROM cart")
    suspend fun getCartSize(): Int

    @Query("SELECT EXISTS (SELECT 1 FROM cart WHERE [key] = :key)")
    suspend fun inCart(key: String): Boolean
}