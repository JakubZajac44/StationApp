package com.jakub.zajac.station.stations.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.domain.use_case.GetAllStationUseCase
import com.jakub.zajac.station.stations.domain.use_case.GetStationsDictionaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getAllStationUseCase: GetAllStationUseCase,
    private val getStationUseCase: GetStationsDictionaryUseCase
) : ViewModel(){

    private val _state: MutableStateFlow<StationListState> = MutableStateFlow(StationListState())
    val state: StateFlow<StationListState>
        get() = _state.asStateFlow()

    init {
        getStationList()
        getStationDictionaryList()
    }


    private fun getStationList(){
        viewModelScope.launch(Dispatchers.IO) {
            getAllStationUseCase().collect{result ->
                when(result){
                    is Resource.Error -> Log.e("Koleo", "Error")
                    is Resource.Loading -> Log.e("Koleo", "Loading")
                    is Resource.Success -> {
                        Log.e("Koleo", "Success")
                        _state.update { data ->
                            data.copy(
                                stationList = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getStationDictionaryList(){
        viewModelScope.launch(Dispatchers.IO) {
            getStationUseCase().collect{result ->
                when(result){
                    is Resource.Error -> Log.e("Koleo", "Error")
                    is Resource.Loading -> Log.e("Koleo", "Loading")
                    is Resource.Success -> {
                        Log.e("Koleo", "Success")
                        _state.update { data ->
                            data.copy(
                                stationDictionaryList = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}