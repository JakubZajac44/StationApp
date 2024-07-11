package com.jakub.zajac.station.stations.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.domain.model.StationModel
import com.jakub.zajac.station.stations.domain.use_case.LoadStationDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.Normalizer


import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStationDataUseCase: LoadStationDataUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState>
        get() = _state.asStateFlow()

    private var job: Job? = null

    init {
        loadData()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchQueryTyped -> {
                job?.cancel()
                job = viewModelScope.launch(Dispatchers.IO) {
                    _state.update { data ->
                        data.copy(
                            searchQuery = event.query, isFiltering = true
                        )
                    }
                    val filteredList = filterStationList(event.query)
                    _state.update { data ->
                        data.copy(
                            stationListFiltered = filteredList, isFiltering = false
                        )
                    }

                }
            }

            SearchEvent.ToggleSearch -> {
                val searchQuery = if (state.value.isSearching) "" else state.value.searchQuery
                _state.update { data ->
                    data.copy(
                        isSearching = !state.value.isSearching, searchQuery = searchQuery
                    )
                }
            }

            SearchEvent.ClearSearchTyped -> {
                _state.update { data ->
                    data.copy(
                        stationListFiltered = listOf(), searchQuery = "", isLoading = false
                    )
                }
            }

            SearchEvent.RefreshData -> loadData()
        }

    }

    private suspend fun filterStationList(query: String): List<StationModel> {
        delay(500)
        return if (query.isBlank()) emptyList()
        else {
            val stationDictionaryListFiltered = state.value.stationDictionaryList.filter {
                it.keyword.removeNonSpacingMarks().contains(
                    query.removeNonSpacingMarks(), ignoreCase = true
                )
            }.map { it.stationId }
            state.value.stationList.filter { it.id in stationDictionaryListFiltered }
                .sortedByDescending { it.hits }
        }
    }


    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            getStationDataUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update { data ->
                            data.copy(
                                errorMessage = result.message ?: "Błąd po stronie serwera",
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { data ->
                            data.copy(
                                isLoading = result.isLoading, errorMessage = ""
                            )
                        }
                    }

                    is Resource.Success -> {
                        Log.e("Koleo", "Success")
                        _state.update { data ->
                            data.copy(
                                stationList = result.data.stationModel,
                                stationDictionaryList = result.data.stationDictionaryModel,
                                isLoading = false,
                                errorMessage = ""
                            )
                        }
                    }
                }
            }
        }
    }

    private fun String.removeNonSpacingMarks() =
        Normalizer.normalize(this, Normalizer.Form.NFD).replace("\\p{Mn}+".toRegex(), "")
}