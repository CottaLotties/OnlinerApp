package com.example.onlinerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onlinerapp.entities.cart.Cart
import com.example.onlinerapp.entities.categories.Category
import com.example.onlinerapp.entities.product.Product

@Database(entities = [Category::class, Product::class, Cart::class], version=1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract  fun categoryDao(): CategoryDao

    /*companion object {
        @Volatile private var dbInstance: AppDatabase? = null

        fun getDB(context: Context):AppDatabase =
                dbInstance ?: synchronized(this) { dbInstance ?: buildDatabase(context).also { dbInstance = it } }

        private fun buildDatabase(appContext: Context) =
                Room.databaseBuilder(appContext, AppDatabase::class.java, "onlinerAppDB")
                        .fallbackToDestructiveMigration()
                        .build()
    }*/
}