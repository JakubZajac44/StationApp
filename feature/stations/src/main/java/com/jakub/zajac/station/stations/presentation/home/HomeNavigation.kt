package com.jakub.zajac.station.stations.presentation.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jakub.zajac.station.stations.presentation.Route


const val STATION_ID_KEY = "stationId"
const val STATION_NAME_KEY = "stationName"
const val STATION_LONGITUDE_KEY = "stationLongitude"
const val STATION_LATITUDE_KEY = "stationLatitude"
const val STATION_TYPE_KEY = "stationType"
const val STATION_TYPE_SOURCE = "source"
const val STATION_TYPE_DESTINATION = "destination"

internal fun NavGraphBuilder.homeNavigation(
    navigationEvent: (HomeNavigationEvent) -> Unit
) {
    composable<Route.HomeScreenRout> { navBackResult ->


        val viewModel: HomeViewModel = hiltViewModel()
        navBackResult.savedStateHandle.apply {

            val stationId = get<Int>(STATION_ID_KEY)?.let { stationId ->
                navBackResult.savedStateHandle.remove<Int>(STATION_ID_KEY)
                stationId
            }

            val stationName = get<String>(STATION_NAME_KEY)?.let { stationName ->
                navBackResult.savedStateHandle.remove<String>(STATION_NAME_KEY)
                stationName
            }

            val stationLongitude = get<Float>(STATION_LONGITUDE_KEY)?.let { stationLongitude ->
                navBackResult.savedStateHandle.remove<Float>(STATION_LONGITUDE_KEY)
                stationLongitude
            }

            val stationLatitude = get<Float>(STATION_LATITUDE_KEY)?.let { stationLatitude ->
                navBackResult.savedStateHandle.remove<Float>(STATION_LATITUDE_KEY)
                stationLatitude
            }


            val type = get<String>(STATION_TYPE_KEY)?.let { stationType ->
                navBackResult.savedStateHandle.remove<String>(STATION_TYPE_KEY)
                stationType
            }
            if (stationId != null && type != null && stationName != null && stationLongitude != null && stationLatitude != null) {
                viewModel.setInitialData(
                    stationId = stationId,
                    stationType = type,
                    stationName = stationName,
                    stationLongitude = stationLongitude,
                    stationLatitude = stationLatitude
                )

            }
        }

        HomeScreen(
            state = viewModel.state.collectAsStateWithLifecycle().value,
            navigationEvent = navigationEvent,
            event = viewModel::onEvent
        )
    }
}
