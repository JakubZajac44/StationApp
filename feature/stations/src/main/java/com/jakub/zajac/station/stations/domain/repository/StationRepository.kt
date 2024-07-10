package com.jakub.zajac.station.stations.domain.repository

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.remote.model.StationDto

interface StationRepository {

    suspend fun getStationList(isRefresh: Boolean = false): ApiResult<List<StationDto>>

}