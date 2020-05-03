package com.restaurantfinder.usecases

import com.restaurantfinder.base.BaseUseCase
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.repository.FavoriteRepository

class GetAllFavoriteRestaurantsUseCase(
    private val repository: FavoriteRepository
): BaseUseCase<Unit, List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant>> {

    override suspend fun perform(input: Unit): List<UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant> {
        return repository.getAllFavorites().map {
            UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant.from(it)
        }
    }


}