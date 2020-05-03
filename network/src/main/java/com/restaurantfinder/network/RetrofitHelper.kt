package com.restaurantfinder.network

import com.restaurantfinder.network.models.LocationSearchApiResult
import com.restaurantfinder.network.models.RestaurantSearchApiResult
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitHelper {

    interface ApiService {

        @GET("search")
        suspend fun searchRestaurants(
            @Query("entity_id") entityId: Int,
            @Query("entity_type") entityType: String = "city",
            @Query("q") searchTerm: String,
            @Query("sort") sortBy: String,
            @Query("order") order: String
        ): Response<RestaurantSearchApiResult>

        @GET("cities")
        suspend fun searchLocation(
            @Query("q") searchTerm: String,
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double
        ): Response<LocationSearchApiResult>


    }

    fun zomatoSearchApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}