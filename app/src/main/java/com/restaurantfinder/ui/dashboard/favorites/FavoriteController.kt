package com.restaurantfinder.ui.dashboard.favorites

import com.airbnb.epoxy.TypedEpoxyController
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.restaurantSearchResult

class FavoriteController: TypedEpoxyController<List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>>() {


    override fun buildModels(data: List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>?) {
        data?.let {
            buildItems(data)
        }
    }

    private fun buildItems(data: List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>) {

        data.forEach { restaurant ->
            restaurantSearchResult {
                id(restaurant.restaurantId)
                restaurant(restaurant)
            }
        }

    }
}