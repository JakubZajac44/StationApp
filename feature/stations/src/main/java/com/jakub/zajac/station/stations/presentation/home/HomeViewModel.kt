package com.jakub.zajac.station.stations.presentation.home

import androidx.lifecycle.ViewModel
import com.jakub.zajac.station.stations.domain.model.StationModel
import com.jakub.zajac.station.stations.domain.use_case.MeasureDistanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val measureDistanceUseCase: MeasureDistanceUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()


    fun setInitialData(
        stationType: String,
        stationId: Int,
        stationName: String,
        stationLongitude: Float,
        stationLatitude: Float
    ) {
        when (stationType) {
            STATION_TYPE_SOURCE -> {
                _state.update { data ->
                    data.copy(
                        sourceStation = StationModel(
                            id = stationId,
                            name = stationName,
                            latitude = stationLatitude,
                            longitude = stationLongitude
                        )
                    )
                }
            }

            STATION_TYPE_DESTINATION -> {
                _state.update { data ->
                    data.copy(
                        destinationStation = StationModel(
                            id = stationId,
                            name = stationName,
                            latitude = stationLatitude,
                            longitude = stationLongitude
                        )
                    )
                }
            }
        }

        state.value.sourceStation?.let { sourceStation ->
            state.value.destinationStation?.let { destinationStation ->

                if(sourceStation.latitude == destinationStation.latitude &&
                    sourceStation.longitude == destinationStation.longitude){
                    _state.update { data ->
                        data.copy(
                            errorTheSameStation = true,
                            distanceBetweenStation = 0.0f
                        )
                    }

                }else{
                    val distanceBetweenStation = measureDistanceUseCase(
                        sourceStation.latitude.toDouble(),
                        sourceStation.longitude.toDouble(),
                        destinationStation.latitude.toDouble(),
                        destinationStation.longitude.toDouble()
                    )

                    _state.update { data ->
                        data.copy(
                            distanceBetweenStation = distanceBetweenStation,
                            errorTheSameStation = false
                        )
                    }
                }
            }
        }
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ClearDestinationStationClicked -> {
                _state.update { data ->
                    data.copy(
                        destinationStation = null,
                        distanceBetweenStation = 0.0f,
                        errorTheSameStation = false
                    )
                }
            }

            HomeEvent.ClearSourceStationClicked -> _state.update { data ->
                data.copy(
                    sourceStation = null,
                    distanceBetweenStation = 0.0f,
                    errorTheSameStation = false
                )
            }
        }
    }
}

