package com.restaurantfinder.repository

import com.restaurantfinder.network.RetrofitHelper
import com.restaurantfinder.network.models.LocationSearchApiResult
import com.restaurantfinder.network.models.RestaurantSearchApiResult
import com.restaurantfinder.network.utils.SafeResult
import com.restaurantfinder.network.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val zomatoApiService: RetrofitHelper.ApiService
) {

    suspend fun searchForRestaurants(
        searchTerm: String,
        sortBy: String,
        order: String,
        cityId: Int
    ): SafeResult<RestaurantSearchApiResult> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                zomatoApiService.searchRestaurants(
                    searchTerm = searchTerm,
                    sortBy = sortBy,
                    order = order,
                    entityId = cityId
                )
            }
        }
    }

    suspend fun searchForLocation(
        searchTerm: String,
        latitude: Double,
        longitude: Double
    ): SafeResult<LocationSearchApiResult> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                zomatoApiService.searchLocation(
                    searchTerm = searchTerm,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
    }

}