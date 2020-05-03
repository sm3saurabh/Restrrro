package com.restaurantfinder.usecases

import android.util.Log
import com.restaurantfinder.base.BaseUseCase
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.network.utils.SafeResult
import com.restaurantfinder.repository.SearchRepository
import com.restaurantfinder.utils.TAG

class RestaurantSearchUseCase(
    private val searchRepository: SearchRepository,
    private val getAllFavoriteRestaurantsUseCase: GetAllFavoriteRestaurantsUseCase
): BaseUseCase<RestaurantSearchUseCase.InputParam, UIModelRestaurantSearch?> {

    data class InputParam(
        val searchTerm: String,
        val filterOption: FilterOption,
        val cityId: Int
    ) {

        data class FilterOption (
            val sortOption: RestaurantSearchUseCase.InputParam.SortOptions,
            val orderOption: RestaurantSearchUseCase.InputParam.OrderOptions
        ) {
            companion object {
                val EMPTY = FilterOption(
                    sortOption = RestaurantSearchUseCase.InputParam.SortOptions.COST,
                    orderOption = RestaurantSearchUseCase.InputParam.OrderOptions.ASC
                )
            }
        }

        enum class SortOptions {
            COST,
            RATING
        }

        enum class OrderOptions {
            ASC,
            DESC
        }
    }

    override suspend fun perform(input: InputParam): UIModelRestaurantSearch? {

        val apiResult = searchRepository.searchForRestaurants(
            searchTerm = input.searchTerm,
            sortBy = input.filterOption.sortOption.name.toLowerCase(),
            order = input.filterOption.orderOption.name.toLowerCase(),
            cityId = input.cityId
        )

        return when (apiResult) {
            is SafeResult.Success -> {
               apiResult.data?.let {

                   val favorites = getAllFavoriteRestaurantsUseCase.perform(Unit).map { it.restaurantId }

                   UIModelRestaurantSearch.from(it, input.searchTerm, favorites)
               }
            }
            is SafeResult.Failure -> {
                Log.e(TAG,"error while calling api", apiResult.exception)
                null
            }
        }

    }


}

