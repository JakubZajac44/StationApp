package com.jakub.zajac.station.stations.domain.model

data class StationModel (
    val id: Int,
    val name: String,
    val hits: Int = 0,
    val latitude: Float,
    val longitude: Float,
)