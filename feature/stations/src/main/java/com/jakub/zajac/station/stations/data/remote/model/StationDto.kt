package com.jakub.zajac.station.stations.data.remote.model

data class StationDto(
    val id: Int,
    val name: String,
    val name_slug: String? = null,
    val latitude: Float,
    val longitude: Float,
    val hits: Int,
    val ibnr: Int? = null,
    val city: String? = null,
    val region: String? = null,
    val country: String? = null,
    val localised_name: String? = null,
    val is_group: Boolean = false,
    val has_announcements: Boolean = false,
    val is_nearby_station_enabled: Boolean = false
)



