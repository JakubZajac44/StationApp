package com.jakub.zajac.station.stations.presentation.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jakub.zajac.station.stations.presentation.Route


internal fun NavGraphBuilder.searchNavigation(
    navigationEvent: (SearchNavigationEvent) -> Unit
) {
    composable<Route.SearchScreenRout> {
        val args = it.toRoute<Route.SearchScreenRout>()
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreen(
            type = args.type?:"",
            state = viewModel.state.collectAsStateWithLifecycle().value,
            event = viewModel::onEvent,
            navigationEvent = navigationEvent
        )
    }
}