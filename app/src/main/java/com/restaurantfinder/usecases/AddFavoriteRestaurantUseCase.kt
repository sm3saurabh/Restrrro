package com.restaurantfinder.usecases

import com.restaurantfinder.base.BaseUseCase
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.repository.FavoriteRepository

class AddFavoriteRestaurantUseCase(
    private val repository: FavoriteRepository
): BaseUseCase<AddFavoriteRestaurantUseCase.InputParam, Boolean> {

    data class InputParam(
        val restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant
    )


    override suspend fun perform(input: InputParam): Boolean {
        val favoriteRestaurantEntity = input.restaurant.toDatabaseEntity()

        return repository.addFavorite(favoriteRestaurantEntity) != -1L
    }

}