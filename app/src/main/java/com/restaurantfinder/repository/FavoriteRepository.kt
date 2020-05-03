package com.restaurantfinder.repository

import androidx.lifecycle.LiveData
import com.restaurantfinder.database.RFDatabase
import com.restaurantfinder.database.entities.FavoriteRestaurantEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(
    private val database: RFDatabase
) {

    suspend fun addFavorite(favoriteRestaurantEntity: FavoriteRestaurantEntity): Long {
        return withContext(Dispatchers.IO) {
            database.favoriteRestaurantDao().insertFavorite(favoriteRestaurantEntity)
        }
    }

    suspend fun deleteFavorite(favoriteRestaurantEntity: FavoriteRestaurantEntity): Int {
        return withContext(Dispatchers.IO) {
            database.favoriteRestaurantDao().deleteFavorite(favoriteRestaurantEntity)
        }
    }

    fun getAllFavoriteLiveData(): LiveData<List<FavoriteRestaurantEntity>> {
        return database.favoriteRestaurantDao().getAllFavoritesLiveData()
    }

    suspend fun getAllFavorites(): List<FavoriteRestaurantEntity> {
        return withContext(Dispatchers.IO) {
            database.favoriteRestaurantDao().getAllFavorites()
        }
    }

}