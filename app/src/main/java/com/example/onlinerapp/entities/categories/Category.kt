package com.example.onlinerapp.entities.categories

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onlinerapp.entities.product.Prices

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: Int,
    val key: String,
    val name: String,
    @Embedded
    val prices : Prices?
)