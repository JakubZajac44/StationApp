package com.jakub.zajac.station.stations.presentation.home

sealed class HomeEvent {
    data object ClearSourceStationClicked: HomeEvent()
    data object ClearDestinationStationClicked: HomeEvent()
}

sealed class HomeNavigationEvent{
    data class StationSearchClick(val type: String): HomeNavigationEvent()
}