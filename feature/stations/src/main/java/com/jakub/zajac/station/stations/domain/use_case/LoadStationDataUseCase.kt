package com.jakub.zajac.station.stations.domain.use_case

import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.domain.model.StationWrapper
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoadStationDataUseCase @Inject constructor(
    private val getStationListUseCase: GetStationsUseCase,
    private val getStationDictionaryListUseCase: GetStationsDictionaryUseCase
) {

    operator fun invoke(): Flow<Resource<StationWrapper>> {
        return flow {
            emit(Resource.Loading())
            coroutineScope {
                val stationListResult = async { getStationListUseCase() }.await()
                val stationDictionaryListResult =
                    async { getStationDictionaryListUseCase() }.await()

                delay(2000)
                if (stationListResult is Resource.Success && stationDictionaryListResult is Resource.Success) {
                    emit(
                        Resource.Success(
                            StationWrapper(
                                stationModel = stationListResult.data,
                                stationDictionaryModel = stationDictionaryListResult.data
                            )
                        )
                    )
                } else if (stationListResult is Resource.Error) {
                    emit(
                        Resource.Error(
                            stationListResult.exception, stationListResult.exception.message
                        )
                    )
                } else if (stationDictionaryListResult is Resource.Error) {
                    emit(
                        Resource.Error(
                            stationDictionaryListResult.exception,
                            stationDictionaryListResult.exception.message
                        )
                    )
                }
            }
        }
    }
}