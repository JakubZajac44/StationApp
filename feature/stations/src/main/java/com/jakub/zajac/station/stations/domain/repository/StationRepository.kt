package com.jakub.zajac.station.stations.domain.repository

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto
import com.jakub.zajac.station.stations.data.remote.model.StationDto

interface StationRepository {

    suspend fun getStationList(): ApiResult<List<StationDto>>

    suspend fun getStationDictionaryList(): ApiResult<List<StationDictionaryDto>>
}