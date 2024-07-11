package com.jakub.zajac.station.stations.presentation.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jakub.zajac.station.stations.presentation.home.compnent.SearchComponent


@Composable
fun HomeScreen(
    state: HomeState, event: (HomeEvent) -> Unit, navigationEvent: (HomeNavigationEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Station App")


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Wybierz stację początkową oraz docelową, aby obliczyć odegłości pomiedzy nimi",
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(20.dp))

            SearchComponent(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape = RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),

                onClickEvent = {
                    navigationEvent.invoke(HomeNavigationEvent.FirstStationSearchClick("source"))
                },
                onClearEvent = {
                    event.invoke(HomeEvent.ClearSourceStationClicked)
                },
                defaultHint = "Wybierz stację początkową",
                currentSearchValue = state.sourceStation?.name
            )

            Spacer(modifier = Modifier.height(20.dp))

            SearchComponent(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape = RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
                onClickEvent = {
                    navigationEvent.invoke(HomeNavigationEvent.FirstStationSearchClick("destination"))
                },
                onClearEvent = {
                    event.invoke(HomeEvent.ClearDestinationStationClicked)
                },
                defaultHint = "Wybierz stację końcową",
                currentSearchValue = state.destinationStation?.name
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (state.distanceBetweenStation > 0) {
                Text("Odległośc między stacjami wynoski: ${state.distanceBetweenStation} km")
            }

        }


    }
}


