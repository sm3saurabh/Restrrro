package com.restaurantfinder.network.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LocationSearchApiResult(
    @SerialName("location_suggestions")
    val suggestedLocations: List<Location> = emptyList(),
    val status: String = "",
    @SerialName("has_more")
    val hasMore: Int = 0
)