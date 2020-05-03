package com.restaurantfinder.models

import com.restaurantfinder.database.entities.FavoriteRestaurantEntity
import com.restaurantfinder.network.models.RestaurantApiResult
import com.restaurantfinder.network.models.RestaurantSearchApiResult

/*
Restaurant search result will be shown to user as follows
    [Cuisine A ]
        [ Restaurant A ]
        [ Restaurant B ]
        [ Restaurant C ]
        ..... so on
    [Cuisine B]
        [ Restaurant D ]
        [ Restaurant E ]
        [ Restaurant A ]
    .... so on
*/

// Since one restaurant can have more than cuisine. The restaurant result can also appear under multiple cuisines
// We may have a thought that this is duplicity, but the decision was made because
// 1. We don't have any data to choose a single cuisine out the list that comes with the restaurant object from the api
// 2. In conjuncture with point 1, I think we should show all the data that the api is sending, since the api is
//    considered the single source of truth here.
data class UIModelRestaurantSearch(
    val searchTerm: String,
    val cuisinesWithRestaurants: List<UIModelCuisineWithRestaurants>
) {

    // Models are nested to signify their limited scope.

    data class UIModelCuisineWithRestaurants(
        val cuisine: UIModelCuisine,
        val restaurants: List<UIModelRestaurant>
    ) {

        data class UIModelCuisine(
            val cuisineTitle: String,
            val isExpanded: Boolean
        )

        data class UIModelRestaurant(
            val restaurantName: String,
            val reviewsCount: Int,
            val averageCostForTwo: Int,
            val currency: String,
            val priceRange: Int,
            val thumbnailUrl: String,
            val locationName: String,
            val userRating: UIModelRestaurantRating,
            val cuisines: String,
            val restaurantId: String,
            val isFavorite: Boolean = false
        ) {
            data class UIModelRestaurantRating(
                val aggregateRating: String,
                val ratingColor: String,
                val votes: String
            )

            fun toDatabaseEntity(): FavoriteRestaurantEntity {
                return FavoriteRestaurantEntity(
                    restaurantId = this.restaurantId,
                    restaurantName = this.restaurantName,
                    reviewsCount = this.reviewsCount,
                    averageCostForTwo = this.averageCostForTwo,
                    currency = this.currency,
                    priceRange = this.priceRange,
                    thumbnailUrl = this.thumbnailUrl,
                    locationName = this.locationName,
                    aggregateRating = this.userRating.aggregateRating,
                    ratingColor = this.userRating.ratingColor,
                    votes = this.userRating.votes,
                    cuisines = this.cuisines
                )
            }

            companion object {
                fun from(entity: FavoriteRestaurantEntity): UIModelRestaurant {
                    return UIModelRestaurant(
                        restaurantId = entity.restaurantId,
                        restaurantName = entity.restaurantName,
                        reviewsCount = entity.reviewsCount,
                        averageCostForTwo = entity.averageCostForTwo,
                        currency = entity.currency,
                        priceRange = entity.priceRange,
                        thumbnailUrl = entity.thumbnailUrl,
                        locationName = entity.locationName,
                        userRating = UIModelRestaurantRating(
                            aggregateRating = entity.aggregateRating,
                            ratingColor = entity.ratingColor,
                            votes = entity.votes
                        ),
                        cuisines = entity.cuisines,
                        isFavorite = true
                    )
                }
            }

        }

    }

    companion object {

        fun from(restaurantSearchResult: RestaurantSearchApiResult, searchTerm: String, favorites: List<String>): UIModelRestaurantSearch {

            val allRestaurants = getAllRestaurants(restaurantSearchResult.restaurants, favorites)

            val allCuisines = getAllCuisines(allRestaurants)


            val cuisinesWithRestaurants = allCuisines.map { cuisine ->
                UIModelCuisineWithRestaurants(
                    cuisine = cuisine,
                    restaurants = allRestaurants.filter { it.cuisines.contains(cuisine.cuisineTitle) }
                )
            }

            return UIModelRestaurantSearch(
                searchTerm = searchTerm,
                cuisinesWithRestaurants = cuisinesWithRestaurants
            )
        }


        private fun getAllCuisines(restaurants: List<UIModelCuisineWithRestaurants.UIModelRestaurant>): List<UIModelCuisineWithRestaurants.UIModelCuisine> {

            return restaurants.flatMap { restaurant ->
                restaurant.cuisines.split(",[ ]*".toRegex())
            }.distinct().map { cuisineTitle ->
                UIModelCuisineWithRestaurants.UIModelCuisine(
                    cuisineTitle = cuisineTitle,
                    isExpanded = true
                )
            }
        }

        private fun getAllRestaurants(restaurants: List<RestaurantApiResult>, favorites: List<String>): List<UIModelCuisineWithRestaurants.UIModelRestaurant> {

            return restaurants.map { restaurantResult ->

                val restaurant = restaurantResult.restaurant

                UIModelCuisineWithRestaurants.UIModelRestaurant(
                    restaurantName = restaurant.name,
                    reviewsCount = restaurant.allReviewsCount,
                    averageCostForTwo = restaurant.averageCostForTwo,
                    currency = restaurant.currency,
                    priceRange = restaurant.priceRange,
                    thumbnailUrl = restaurant.thumb,
                    locationName = restaurant.location.localityVerbose,
                    userRating = UIModelCuisineWithRestaurants.UIModelRestaurant.UIModelRestaurantRating(
                        aggregateRating = restaurant.userRating.aggregateRating,
                        ratingColor = restaurant.userRating.ratingColor,
                        votes = restaurant.userRating.votes
                    ),
                    cuisines = restaurant.cuisines,
                    restaurantId = restaurant.id,
                    isFavorite = favorites.contains(restaurant.id)
                )
            }
        }

        val EMPTY = UIModelRestaurantSearch(
            "",
            emptyList()
        )

    }

}