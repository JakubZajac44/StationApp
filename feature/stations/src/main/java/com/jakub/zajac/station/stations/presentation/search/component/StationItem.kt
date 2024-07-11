package com.jakub.zajac.station.stations.presentation.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.jakub.zajac.station.stations.domain.model.StationModel

@Composable
fun StationItem(
    station: StationModel, modifier: Modifier = Modifier, stationClickedEvent: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                stationClickedEvent.invoke()
            }) {
        Text(
            text = station.name, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
    }

}