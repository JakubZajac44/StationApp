package com.jakub.zajac.station.stations.domain.repository

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto

interface StationDictionaryRepository {

    suspend fun getStationDictionaryList(isRefresh: Boolean = false): ApiResult<List<StationDictionaryDto>>
}