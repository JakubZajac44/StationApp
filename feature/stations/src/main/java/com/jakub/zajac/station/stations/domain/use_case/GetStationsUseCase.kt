package com.jakub.zajac.station.stations.domain.use_case

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.data.mapper.toStationModel
import com.jakub.zajac.station.stations.domain.model.StationModel
import com.jakub.zajac.station.stations.domain.repository.StationRepository
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val stationRepository: StationRepository,
) {
    suspend operator fun invoke(): Resource<List<StationModel>> {
        return when (val apiResponse = stationRepository.getStationList()) {
            is ApiResult.Error -> {
                Resource.Error(apiResponse.exception, apiResponse.exception.message)
            }

            is ApiResult.Success -> {
                Resource.Success(apiResponse.data.map { it.toStationModel() }.sortedBy { it.hits })
            }
        }
    }
}