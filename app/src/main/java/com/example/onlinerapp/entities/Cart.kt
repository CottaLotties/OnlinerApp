package com.example.onlinerapp.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
        @PrimaryKey(autoGenerate = true)
        val selected_product_id: Int,
        @Embedded
        val selected_product: Product
)