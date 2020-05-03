package com.restaurantfinder.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RestaurantApiResult(
    val restaurant: Restaurant = Restaurant()
)

@Serializable
data class Restaurant(
    @SerialName("all_reviews_count")
    val allReviewsCount: Int = 0,
    @SerialName("average_cost_for_two")
    val averageCostForTwo: Int = 0,
    @SerialName("cuisines")
    val cuisines: String = "",
    @SerialName("currency")
    val currency: String = "",
    @SerialName("establishment")
    val establishment: List<String> = listOf(),
    @SerialName("featured_image")
    val featuredImage: String = "",
    @SerialName("highlights")
    val highlights: List<String> = listOf(),
    @SerialName("id")
    val id: String = "",
    val location: RestaurantLocation = RestaurantLocation(),
    val name: String = "",
    @SerialName("price_range")
    val priceRange: Int = 0,
    @SerialName("store_type")
    val storeType: String = "",
    val thumb: String = "",
    val timings: String = "",
    @SerialName("user_rating")
    val userRating: UserRating = UserRating()
)

@Serializable
data class RestaurantLocation(
    val address: String = "",
    val city: String = "",
    @SerialName("city_id")
    val cityId: Int = 0,
    @SerialName("country_id")
    val countryId: Int = 0,
    val latitude: String = "",
    val locality: String = "",
    @SerialName("locality_verbose")
    val localityVerbose: String = "",
    val longitude: String = "",
    val zipcode: String = ""
)

@Serializable
data class UserRating(
    @SerialName("aggregate_rating")
    val aggregateRating: String = "",
    @SerialName("rating_color")
    val ratingColor: String = "",
    @SerialName("rating_obj")
    val ratingObj: RatingObj = RatingObj(),
    @SerialName("rating_text")
    val ratingText: String = "",
    val votes: String = ""
)

@Serializable
data class RatingObj(
    @SerialName("bg_color")
    val bgColor: BgColor = BgColor(),
    val title: Title = Title()
)

@Serializable
data class BgColor(
    val tint: String = "",
    val type: String = ""
)

@Serializable
data class Title(
    val text: String = ""
)