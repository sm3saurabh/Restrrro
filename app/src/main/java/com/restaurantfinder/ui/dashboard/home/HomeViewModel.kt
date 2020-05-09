package com.restaurantfinder.ui.dashboard.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurantfinder.models.UIModelHome
import com.restaurantfinder.models.UIModelRestaurantSearch
import com.restaurantfinder.usecases.AddFavoriteRestaurantUseCase
import com.restaurantfinder.usecases.DeleteFavoriteRestaurantUseCase
import com.restaurantfinder.usecases.LocationSearchUseCase
import com.restaurantfinder.usecases.RestaurantSearchUseCase
import com.restaurantfinder.utils.safeLaunch

class HomeViewModel(
    private val searchUseCase: RestaurantSearchUseCase,
    private val locationSearchUseCase: LocationSearchUseCase,
    private val addFavoriteRestaurantUseCase: AddFavoriteRestaurantUseCase,
    private val deleteFavoriteRestaurantUseCase: DeleteFavoriteRestaurantUseCase
) : ViewModel() {

    private val _data = MutableLiveData<UIModelHome>().apply {
        value = UIModelHome.EMPTY
    }
    val data: LiveData<UIModelHome> = _data

    private val _filterOption = MutableLiveData<RestaurantSearchUseCase.InputParam.FilterOption>().apply {
        value = RestaurantSearchUseCase.InputParam.FilterOption.EMPTY
    }
    val filterOption: LiveData<RestaurantSearchUseCase.InputParam.FilterOption> = _filterOption


    fun restaurantSearch(searchTerm: String) {
        if (searchTerm.isNotBlank()) {
            _data.value?.let { home ->
                viewModelScope.safeLaunch {
                    val restaurantSearchResult = searchUseCase.perform(
                        RestaurantSearchUseCase.InputParam(
                            searchTerm,
                            _filterOption.value
                                ?: RestaurantSearchUseCase.InputParam.FilterOption.EMPTY,
                            home.currentLocation.locationId
                        )
                    )


                    restaurantSearchResult?.let {
                        _data.value = home.copy(
                            restaurantSearchResult = restaurantSearchResult,
                            currentHomeState = HomeState.SearchResult
                        )
                    }

                }
            }
        }

    }

    fun locationSearch(getLocation: suspend () -> Location?) {
        _data.value?.let { home ->
            viewModelScope.safeLaunch {
                getLocation()?.let { location ->

                    val locationSearchResult = locationSearchUseCase.perform(
                        LocationSearchUseCase.InputParam(
                            searchTerm = "",
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )

                    locationSearchResult?.let {
                        _data.value = home.copy(currentLocation = locationSearchResult)
                    }
                }
            }
        }
    }

    fun updateHomeState(state: HomeState) {
        _data.value?.let { home ->
            _data.value = home.copy(currentHomeState = state)
        }
    }

    fun updateFilterOption(filterOption: RestaurantSearchUseCase.InputParam.FilterOption) {
        _filterOption.value?.let { option ->
            if (filterOption != option) {
                _filterOption.value = filterOption
            }
        }
    }

    fun updateFavorite(restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant) {

        if (restaurant.isFavorite) {
            removeFavorite(restaurant)
        } else {
            addFavorite(restaurant)
        }

        _data.value?.let { home ->
            val newCuisineWithResult = home.restaurantSearchResult.cuisinesWithRestaurants.map {

                val newRestaurants = it.restaurants.map { ogRestaurant ->
                    if (ogRestaurant.restaurantId == restaurant.restaurantId) {
                        ogRestaurant.copy(isFavorite = !ogRestaurant.isFavorite)
                    } else {
                        ogRestaurant
                    }
                }

                it.copy(restaurants = newRestaurants)

            }

            _data.value = home.copy(restaurantSearchResult = home.restaurantSearchResult.copy(cuisinesWithRestaurants = newCuisineWithResult))
        }

    }

    private fun removeFavorite(restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant) {
        viewModelScope.safeLaunch {
            deleteFavoriteRestaurantUseCase.perform(DeleteFavoriteRestaurantUseCase.InputParam(
                restaurant = restaurant
            ))
        }
    }

    private fun addFavorite(restaurant: UIModelRestaurantSearch.UIModelCuisineWithRestaurants.UIModelRestaurant) {
        viewModelScope.safeLaunch {
            addFavoriteRestaurantUseCase.perform(
                AddFavoriteRestaurantUseCase.InputParam(
                restaurant = restaurant
            ))
        }
    }


}

sealed class HomeState {
    object Idle : HomeState()
    object Searching : HomeState()
    object SearchEditing : HomeState()
    object SearchResult : HomeState()
}

