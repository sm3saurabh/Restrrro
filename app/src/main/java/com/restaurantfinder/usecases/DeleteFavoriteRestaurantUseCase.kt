package com.restaurantfinder.usecases

import com.restaurantfinder.base.BaseUseCase
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.repository.FavoriteRepository

class DeleteFavoriteRestaurantUseCase(
    private val repository: FavoriteRepository
): BaseUseCase<DeleteFavoriteRestaurantUseCase.InputParam, Boolean>{

    data class InputParam(
        val restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant
    )

    override suspend fun perform(input: InputParam): Boolean {
        val favoriteRestaurantEntity = input.restaurant.toDatabaseEntity()

        return repository.deleteFavorite(favoriteRestaurantEntity) == 1
    }



}