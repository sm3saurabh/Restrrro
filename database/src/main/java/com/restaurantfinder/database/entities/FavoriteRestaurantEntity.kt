package com.restaurantfinder.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteRestaurantEntity(
    @PrimaryKey val restaurantId: String,
    val restaurantName: String,
    val reviewsCount: Int,
    val averageCostForTwo: Int,
    val currency: String,
    val priceRange: Int,
    val thumbnailUrl: String,
    val locationName: String,
    val aggregateRating: String,
    val ratingColor: String,
    val votes: String,
    val cuisines: String
)
