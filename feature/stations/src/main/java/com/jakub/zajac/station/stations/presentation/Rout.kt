package com.jakub.zajac.station.stations.presentation

import kotlinx.serialization.Serializable

@Serializable
object GraphRoute

@Serializable
sealed class Route {
    @Serializable
    data class SearchScreenRout(
        val type: String?
    ) : Route()

    @Serializable
    data object HomeScreenRout : Route()
}