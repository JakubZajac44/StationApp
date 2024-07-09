package com.jakub.zajac.station.stations.data.remote.data_source

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.resource.apiCall
import com.jakub.zajac.station.stations.data.remote.api.StationApi
import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto
import com.jakub.zajac.station.stations.data.remote.model.StationDto
import javax.inject.Inject

class StationRemoteDataSource @Inject constructor(
    private val api: StationApi
) {

    suspend fun getStationList(): ApiResult<List<StationDto>> {
        return apiCall(call = { api.getStation() })
    }

    suspend fun getStationDictionary(): ApiResult<List<StationDictionaryDto>>{
        return apiCall(call = { api.getStationDictionary() })
    }
}