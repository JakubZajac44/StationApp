package com.jakub.zajac.station.stations.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jakub.zajac.station.stations.presentation.home.HomeNavigationEvent
import com.jakub.zajac.station.stations.presentation.home.*
import com.jakub.zajac.station.stations.presentation.home.homeNavigation
import com.jakub.zajac.station.stations.presentation.search.SearchNavigationEvent
import com.jakub.zajac.station.stations.presentation.search.searchNavigation


fun NavGraphBuilder.stationGraph(
    navController: NavController,
) {
    navigation<GraphRoute>(
        startDestination = Route.HomeScreenRout
    ) {
        homeNavigation(navigationEvent = { navigationEvent ->
            when (navigationEvent) {
                is HomeNavigationEvent.FirstStationSearchClick -> navController.navigate(
                    Route.SearchScreenRout(type = navigationEvent.type)
                )
            }

        })

        searchNavigation(navigationEvent = { navigationEvent ->
            when (navigationEvent) {
                SearchNavigationEvent.OnBackClicked -> navController.navigateUp()
                is SearchNavigationEvent.StationSelected -> {
                    navController.navigateUp()
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                            set(STATION_ID_KEY, navigationEvent.stationModel.id)
                            set(STATION_NAME_KEY, navigationEvent.stationModel.name)
                            set(STATION_LONGITUDE_KEY, navigationEvent.stationModel.longitude)
                            set(STATION_LATITUDE_KEY, navigationEvent.stationModel.latitude)
                            set(STATION_TYPE_KEY, navigationEvent.stationType)
                        }

                }
            }
        })
    }
}

