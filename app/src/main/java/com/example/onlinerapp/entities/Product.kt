package com.example.onlinerapp.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
        @PrimaryKey
        val id: Int,
        val key: String,
        val name: String,
        val fullName: String?,
        val description: String,
        val html_url: String,
        @Embedded
        val prices: Prices?,
        @Embedded
        val images: Images
)