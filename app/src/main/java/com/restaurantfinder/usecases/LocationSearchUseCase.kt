package com.restaurantfinder.usecases

import android.util.Log
import com.restaurantfinder.base.BaseUseCase
import com.restaurantfinder.models.UIModelLocation
import com.restaurantfinder.network.utils.SafeResult
import com.restaurantfinder.repository.SearchRepository
import com.restaurantfinder.utils.TAG

class LocationSearchUseCase(
    private val searchRepository: SearchRepository
): BaseUseCase<LocationSearchUseCase.InputParam, UIModelLocation?> {

    data class InputParam(
        val searchTerm: String,
        val latitude: Double,
        val longitude: Double
    )

    override suspend fun perform(input: InputParam): UIModelLocation? {
        val locationSearchResult = searchRepository.searchForLocation(
            searchTerm = input.searchTerm,
            latitude = input.latitude,
            longitude = input.longitude
        )

        return when (locationSearchResult) {
            is SafeResult.Success -> {
                locationSearchResult.data?.let {
                    if (it.suggestedLocations.isNotEmpty()) {
                        UIModelLocation.from(it.suggestedLocations[0])
                    } else null
                }
            }

            is SafeResult.Failure -> {
                Log.e(TAG, "Error in location search api call", locationSearchResult.exception)
                null
            }
        }
    }

}