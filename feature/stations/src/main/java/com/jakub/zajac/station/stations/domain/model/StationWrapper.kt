package com.jakub.zajac.station.stations.domain.model

data class StationWrapper(
    val stationDictionaryModel: List<StationDictionaryModel>,
    val stationModel: List<StationModel>
)
