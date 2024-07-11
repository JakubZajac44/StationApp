package com.jakub.zajac.station.stations.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.domain.use_case.FilterStationUseCase
import com.jakub.zajac.station.stations.domain.use_case.LoadStationDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStationDataUseCase: LoadStationDataUseCase,
    private val filterStationUseCase: FilterStationUseCase
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
                    val filteredList = filterStationUseCase(
                        query = event.query,
                        stationDictionaryList = state.value.stationDictionaryList,
                        stationList = state.value.stationList
                    )
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

}