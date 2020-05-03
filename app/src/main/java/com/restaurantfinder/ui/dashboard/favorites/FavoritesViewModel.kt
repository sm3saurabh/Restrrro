package com.restaurantfinder.ui.dashboard.favorites

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.repository.FavoriteRepository

class FavoritesViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {


    fun getAllFavorites() = Transformations.map(favoriteRepository.getAllFavoriteLiveData()) {
        it.map { entity ->
            UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant.from(entity)
        }
    }


}