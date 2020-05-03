package com.restaurantfinder.models

import com.restaurantfinder.ui.dashboard.home.HomeState


data class UIModelHome(
    val currentLocation: UIModelLocation,
    val restaurantSearchResult: UIModelRestaurantSearch,
    val currentHomeState: HomeState
) {
    companion object {
        val EMPTY = UIModelHome(
            currentLocation = UIModelLocation.EMPTY,
            restaurantSearchResult = UIModelRestaurantSearch.EMPTY,
            currentHomeState = HomeState.Idle
        )
    }
}

