package com.jakub.zajac.station.stations.presentation.search

import com.jakub.zajac.station.stations.domain.model.StationModel


sealed class SearchEvent {
    data class SearchQueryTyped(val query: String) : SearchEvent()
    data object ToggleSearch: SearchEvent()
    data object ClearSearchTyped: SearchEvent()
    data object RefreshData: SearchEvent()
}

sealed class SearchNavigationEvent {
    data object OnBackClicked : SearchNavigationEvent()
    data class StationSelected(val stationModel: StationModel, val stationType: String) : SearchNavigationEvent()
}


