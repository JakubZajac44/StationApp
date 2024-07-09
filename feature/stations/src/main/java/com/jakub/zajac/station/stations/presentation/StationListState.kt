package com.jakub.zajac.station.stations.presentation

import com.jakub.zajac.station.stations.domain.model.StationDictionaryModel
import com.jakub.zajac.station.stations.domain.model.StationModel

data class StationListState (
    val stationList: List<StationModel> = listOf(),
    val stationDictionaryList: List<StationDictionaryModel> = listOf()
)