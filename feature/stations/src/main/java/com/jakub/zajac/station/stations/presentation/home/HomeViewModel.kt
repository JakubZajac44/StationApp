package com.jakub.zajac.station.stations.presentation.home

import android.location.Location
import androidx.lifecycle.ViewModel
import com.jakub.zajac.station.stations.domain.model.StationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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


        if (_state.value.sourceStation != null && _state.value.destinationStation != null) {
            _state.update { data ->
                data.copy(
                    distanceBetweenStation = calculateDistance(
                        state.value.sourceStation!!.latitude.toDouble(),
                        state.value.sourceStation!!.longitude.toDouble(),
                        state.value.destinationStation!!.latitude.toDouble(),
                        state.value.destinationStation!!.longitude.toDouble()
                    )
                )
            }
        }


    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ClearDestinationStationClicked -> {
                _state.update { data ->
                    data.copy(
                        destinationStation = null, distanceBetweenStation = 0.0f
                    )
                }
            }

            HomeEvent.ClearSourceStationClicked -> _state.update { data ->
                data.copy(
                    sourceStation = null, distanceBetweenStation = 0.0f
                )
            }
        }

    }

    private fun calculateDistance(sourceLat: Double, sourceLon: Double, destinationLat: Double, destinationLon: Double ):Float{
        val locationA = Location("point A")

        locationA.latitude = sourceLat
        locationA.longitude = sourceLon

        val locationB = Location("point B")

        locationB.latitude = destinationLat
        locationB.longitude = destinationLon

        return locationA.distanceTo(locationB) / 1000
    }



}

