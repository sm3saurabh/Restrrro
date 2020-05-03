package com.restaurantfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.restaurantfinder.database.dao.FavoriteRestaurantDao
import com.restaurantfinder.database.entities.FavoriteRestaurantEntity

@Database(
    entities = [
        FavoriteRestaurantEntity::class
    ], version = 1
)
abstract class RFDatabase : RoomDatabase() {

    abstract fun favoriteRestaurantDao(): FavoriteRestaurantDao

    companion object {
        fun getDatabase(context: Context): RFDatabase {
            return Room.databaseBuilder(context, RFDatabase::class.java, "rf_db")
                .build()
        }
    }

}