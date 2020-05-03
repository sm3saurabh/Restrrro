package com.restaurantfinder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.restaurantfinder.database.entities.FavoriteRestaurantEntity

@Dao
interface FavoriteRestaurantDao {

    @Query("SELECT * FROM FavoriteRestaurantEntity")
    fun getAllFavoritesLiveData(): LiveData<List<FavoriteRestaurantEntity>>

    @Query("SELECT * FROM FavoriteRestaurantEntity")
    suspend fun getAllFavorites(): List<FavoriteRestaurantEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteRestaurantEntity: FavoriteRestaurantEntity): Long

    @Update
    suspend fun updateFavorite(favoriteRestaurantEntity: FavoriteRestaurantEntity): Int

    @Delete
    suspend fun deleteFavorite(favoriteRestaurantEntity: FavoriteRestaurantEntity): Int

    @Query("DELETE FROM FavoriteRestaurantEntity")
    suspend fun deleteAllFavorites()

}