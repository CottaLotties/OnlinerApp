package com.example.onlinerapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlinerapp.entities.Cart
import com.example.onlinerapp.entities.Category
import com.example.onlinerapp.entities.Product

@Database(entities = [Category::class, Product::class, Cart::class], version=1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract  fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var dbInstance: AppDatabase? = null

        fun getDB(context: Context):AppDatabase =
                dbInstance ?: synchronized(this) { dbInstance ?: buildDatabase(context).also { dbInstance = it } }

        private fun buildDatabase(appContext: Context) =
                Room.databaseBuilder(appContext, AppDatabase::class.java, "onlinerAppDB")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}