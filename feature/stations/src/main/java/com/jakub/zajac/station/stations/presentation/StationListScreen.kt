package com.jakub.zajac.station.stations.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StationListScreen(
     state: StationListState
) {



    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            Text("Station list size :${state.stationList.size}")
            Text("Station dictionary list size :${state.stationDictionaryList.size}")
        }

    }
}