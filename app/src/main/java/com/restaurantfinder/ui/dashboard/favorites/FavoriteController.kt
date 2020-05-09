package com.restaurantfinder.ui.dashboard.favorites

import com.airbnb.epoxy.TypedEpoxyController
import com.restaurantfinder.emptyFavoriteLayout
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.restaurantSearchResult

class FavoriteController :
    TypedEpoxyController<List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>>() {


    override fun buildModels(data: List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>?) {
        data?.let {
            buildItems(data)
        }
    }

    private fun buildItems(data: List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>) {

        if (data.isEmpty()) {
            buildEmptyFavoriteItem()
        } else {
            buildFavoriteRestaurantItem(data)
        }

    }

    private fun buildEmptyFavoriteItem() {
        emptyFavoriteLayout {
            id("Oh my god!! We don't have any favorites man.")
        }
    }

    private fun buildFavoriteRestaurantItem(restuarants: List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>) {
        restuarants.forEach { restaurant ->
            restaurantSearchResult {
                id(restaurant.restaurantId)
                restaurant(restaurant)
            }
        }
    }
}