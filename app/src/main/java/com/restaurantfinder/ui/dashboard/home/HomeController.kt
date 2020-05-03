package com.restaurantfinder.ui.dashboard.home

import com.airbnb.epoxy.TypedEpoxyController
import com.restaurantfinder.cuisineResult
import com.restaurantfinder.homeCurrentLocation
import com.restaurantfinder.models.UIModelHome
import com.restaurantfinder.models.UIModelLocation
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.restaurantSearchResult
import com.restaurantfinder.searchingStateText

class HomeController(private val eventHandler: EventHandler) :
    TypedEpoxyController<UIModelHome>() {

    interface EventHandler {
        fun onRestaurantFavoriteToggled(restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant)
    }

    override fun buildModels(data: UIModelHome?) {
        data?.let {
            buildItems(data)
        }
    }

    private fun buildItems(data: UIModelHome) {
        when (data.currentHomeState) {
            HomeState.Idle -> buildIdleHomeState(data.currentLocation)
            HomeState.Searching -> buildSearchingItem()
            HomeState.SearchResult -> buildSearchResults(data.restaurantSearchResult)
        }
    }

    private fun buildIdleHomeState(currentLocation: UIModelLocation) {
        homeCurrentLocation {
            id("Home Current location")
            currentLocationName(currentLocation.getFullLocationName())
        }
    }

    private fun buildSearchingItem() {
        searchingStateText {
            id("Searching state text")
        }
    }

    private fun buildSearchResults(searchResult: UIModelRestaurantSearch) {
        searchResult.cuisinesWithRestaurants.forEach {
            buildCuisineItem(it.cuisine)

            if (it.cuisine.isExpanded) {
                buildRestaurantItems(it.cuisine.cuisineTitle, it.restaurants)
            }

        }
    }

    private fun buildCuisineItem(cuisine: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelCuisine) {
        cuisineResult {
            id(cuisine.cuisineTitle)
            cuisineTitle(cuisine.cuisineTitle)
        }
    }

    private fun buildRestaurantItems(cuisine: String, restaurants: List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>) {
        restaurants.forEach { restaurant ->

            restaurantSearchResult {
                id(cuisine, restaurant.restaurantId)
                restaurant(restaurant)
                favoriteClickListener {_, _, _, _ ->
                    eventHandler.onRestaurantFavoriteToggled(restaurant)
                }
            }

        }
    }



}