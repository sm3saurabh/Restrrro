package com.restaurantfinder.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantSearchApiResult(
    @SerialName("results_found")
    val resultsFound: Int = 0,
    @SerialName("results_start")
    val resultsStart: Int = 0,
    @SerialName("results_shown")
    val resultsShown: Int = 0,
    val restaurants: List<RestaurantApiResult> = emptyList()
)