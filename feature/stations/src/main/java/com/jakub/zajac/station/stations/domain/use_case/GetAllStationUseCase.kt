package com.jakub.zajac.station.stations.domain.use_case

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.data.mapper.toStationModel
import com.jakub.zajac.station.stations.domain.repository.StationRepository
import com.jakub.zajac.station.stations.domain.model.StationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllStationUseCase @Inject constructor(
    private val stationRepository: StationRepository,
) {

    operator fun invoke(): Flow<Resource<List<StationModel>>> {
        return flow {
            emit(Resource.Loading())
            when (val apiResponse = stationRepository.getStationList()) {
                is ApiResult.Error -> {
                    emit(Resource.Error(apiResponse.exception, apiResponse.exception.message))
                }

                is ApiResult.Success -> {
                    emit(Resource.Success(apiResponse.data.map { it.toStationModel() }
                        .sortedBy { it.hits }))
                }

            }
        }
    }
}