package com.jakub.zajac.station.stations.domain.use_case

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.resource.Resource
import com.jakub.zajac.station.stations.data.mapper.toStationDictionaryModel
import com.jakub.zajac.station.stations.data.mapper.toStationModel
import com.jakub.zajac.station.stations.domain.model.StationDictionaryModel
import com.jakub.zajac.station.stations.domain.model.StationModel
import com.jakub.zajac.station.stations.domain.repository.StationDictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStationsDictionaryUseCase @Inject constructor(
    private val stationRepository: StationDictionaryRepository,
) {
    suspend operator fun invoke(): Resource<List<StationDictionaryModel>> {
        return when (val apiResponse = stationRepository.getStationDictionaryList()) {
            is ApiResult.Error -> {
                Resource.Error(apiResponse.exception, apiResponse.exception.message)
            }

            is ApiResult.Success -> {
                Resource.Success(apiResponse.data.map { it.toStationDictionaryModel() })
            }

        }
    }
}