package com.restaurantfinder.models

import com.restaurantfinder.network.models.Location
import com.restaurantfinder.utils.AppConstants

data class UIModelLocation(
    val locationName: String,
    val countryName: String,
    val stateName: String,
    val countryFlagUrl: String,
    val locationId: Int
) {

    fun getFullLocationName(): String {

        val list =  mutableListOf<String>()

        if (locationName.isNotBlank()) {
            list.add(locationName)
        }

        if (stateName.isNotBlank()) {
            list.add(stateName)
        }

        if (countryName.isNotBlank()) {
            list.add(countryName)
        }

        return list.joinToString()
    }

    companion object {
        val EMPTY: UIModelLocation = UIModelLocation(
            locationName = "",
            countryName = "",
            stateName = "",
            countryFlagUrl = "",
            locationId = AppConstants.HYDERABAD_CITY_ID
        )

        fun from(location: Location): UIModelLocation {
            return UIModelLocation(
                locationName = location.name,
                countryName = location.countryName,
                stateName = location.stateName,
                countryFlagUrl = location.countryFlagUrl,
                locationId = location.id
            )
        }
    }
}