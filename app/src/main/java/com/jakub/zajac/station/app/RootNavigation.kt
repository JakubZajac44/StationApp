package com.jakub.zajac.station.app


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jakub.zajac.station.stations.presentation.GraphRoute
import com.jakub.zajac.station.stations.presentation.stationGraph

@Composable
fun RootNavigation(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = GraphRoute
    ) {
        stationGraph(
            navController = navController
        )
    }

}