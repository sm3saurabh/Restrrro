package com.restaurantfinder.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Location(
    @SerialName("country_flag_url")
    val countryFlagUrl: String = "",
    @SerialName("country_id")
    val countryId: Int = 0,
    @SerialName("country_name")
    val countryName: String = "",
    @SerialName("discovery_enabled")
    val discoveryEnabled: Int = 0,
    val id: Int = 0,
    @SerialName("is_state")
    val isState: Int = 0,
    val name: String = "",
    @SerialName("state_code")
    val stateCode: String = "",
    @SerialName("state_id")
    val stateId: Int = 0,
    @SerialName("state_name")
    val stateName: String = ""
)