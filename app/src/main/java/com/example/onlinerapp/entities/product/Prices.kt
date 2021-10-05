package com.example.onlinerapp.entities.product

import androidx.room.Embedded

data class Prices(
    @Embedded
    val price_min: MinPrice?,
    @Embedded
    val offers: Offers
)