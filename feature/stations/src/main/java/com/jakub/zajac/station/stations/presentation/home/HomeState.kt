package com.jakub.zajac.station.stations.presentation.home

import com.jakub.zajac.station.stations.domain.model.StationModel

data class HomeState(
    val sourceStation: StationModel? = null,
    val destinationStation: StationModel? = null,
    val distanceBetweenStation: Float = 0.0f
)