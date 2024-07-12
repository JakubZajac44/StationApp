package com.jakub.zajac.station.stations.presentation.search

import com.jakub.zajac.station.stations.domain.model.StationDictionaryModel
import com.jakub.zajac.station.stations.domain.model.StationModel

data class SearchState (
    val stationList: List<StationModel> = listOf(),
    val stationListFiltered: List<StationModel> = listOf(),
    val stationDictionaryList: List<StationDictionaryModel> = listOf(),
    val isLoading: Boolean = false,
    val isFiltering: Boolean = false,
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val errorMessage: String = ""

)