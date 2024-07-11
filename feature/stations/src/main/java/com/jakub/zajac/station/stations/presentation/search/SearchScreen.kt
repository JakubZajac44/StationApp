package com.jakub.zajac.station.stations.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakub.zajac.station.resource.ui.dot_progress_bar.ProgressDotIndicator
import com.jakub.zajac.station.stations.R
import com.jakub.zajac.station.stations.presentation.search.component.StationItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    type: String,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigationEvent: (SearchNavigationEvent) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .blur(if (state.isLoading) 10.dp else 0.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navigationEvent.invoke(SearchNavigationEvent.OnBackClicked)
                    },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.search_title),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 20.sp)
                )
            }


            SearchBar(
                query = state.searchQuery,
                onQueryChange = { query ->
                    event.invoke(SearchEvent.SearchQueryTyped(query))
                },
                onSearch = {},
                placeholder = {
                    Text(text = stringResource(R.string.search_bar_place_holder))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { event.invoke(SearchEvent.ClearSearchTyped) },
                        imageVector = Icons.Default.Clear,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                active = state.isSearching,
                onActiveChange = { event.invoke(SearchEvent.ToggleSearch) },
                tonalElevation = 0.dp,
                modifier = Modifier.fillMaxSize(),
                enabled = state.errorMessage.isBlank()

            ) {

                Box(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(if (state.isFiltering) 10.dp else 0.dp)
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(32.dp),
                            contentPadding = PaddingValues(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {

                            items(items = state.stationListFiltered, key = {
                                it.id
                            }) { station ->
                                StationItem(station = station) {
                                    navigationEvent.invoke(
                                        SearchNavigationEvent.StationSelected(
                                            stationModel = station, stationType = type
                                        )
                                    )
                                }
                            }

                        }
                    }

                    if (state.isFiltering) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ProgressDotIndicator(
                                modifier = Modifier.size(80.dp),
                                progressColor = MaterialTheme.colorScheme.primary,
                            )
                        }

                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (state.stationListFiltered.isEmpty() && state.searchQuery.isBlank()) {
                                Text(stringResource(R.string.search_start_typig))
                            } else if (state.stationListFiltered.isEmpty() && state.searchQuery.isNotBlank()) {
                                Text(
                                    stringResource(R.string.search_no_result),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                    }

                }

            }
        }

        if (state.errorMessage.isNotBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.search_error_message, state.errorMessage),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = { event.invoke(SearchEvent.RefreshData) }) {
                        Text(text = stringResource(R.string.search_refresh_button))
                    }
                }


            }
        }


        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                ProgressDotIndicator(
                    modifier = Modifier.size(80.dp),
                    progressColor = MaterialTheme.colorScheme.primary,
                )
            }

        }
    }
}
