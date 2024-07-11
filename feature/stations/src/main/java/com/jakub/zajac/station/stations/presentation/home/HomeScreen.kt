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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakub.zajac.station.stations.R
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
            Text(
                text = stringResource(R.string.home_app_name),
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
            )


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                stringResource(R.string.home_sub_title), textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(20.dp))

            SearchComponent(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape = RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),

                onClickEvent = {
                    navigationEvent.invoke(
                        HomeNavigationEvent.StationSearchClick(
                            STATION_TYPE_SOURCE
                        )
                    )
                },
                onClearEvent = {
                    event.invoke(HomeEvent.ClearSourceStationClicked)
                },
                defaultHint = stringResource(R.string.home_search_source_hint),
                currentSearchValue = state.sourceStation?.name
            )

            Spacer(modifier = Modifier.height(20.dp))

            SearchComponent(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(shape = RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
                onClickEvent = {
                    navigationEvent.invoke(
                        HomeNavigationEvent.StationSearchClick(
                            STATION_TYPE_DESTINATION
                        )
                    )
                },
                onClearEvent = {
                    event.invoke(HomeEvent.ClearDestinationStationClicked)
                },
                defaultHint = stringResource(R.string.home_search_destination_hint),
                currentSearchValue = state.destinationStation?.name
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (state.errorTheSameStation) {
                Text(
                    stringResource(R.string.home_error_the_same_station),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            } else if (state.distanceBetweenStation > 0) {
                Text(
                    stringResource(R.string.home_station_distance, state.distanceBetweenStation),
                    textAlign = TextAlign.Center
                )
            }

        }


    }
}


