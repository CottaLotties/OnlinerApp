package com.example.onlinerapp.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: Int,
    val key: String,
    val name: String,
    @Embedded
    val prices : Prices?
)