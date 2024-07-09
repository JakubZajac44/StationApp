package com.jakub.zajac.station.stations.data.repository

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.local.data_source.StationLocalDataSource
import com.jakub.zajac.station.stations.data.remote.data_source.StationRemoteDataSource
import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto
import com.jakub.zajac.station.stations.domain.repository.StationDictionaryRepository
import javax.inject.Inject

class StationDictionaryRepositoryImpl @Inject constructor(
    private val stationRemoteDataSource: StationRemoteDataSource,
    private val stationLocalDataSource: StationLocalDataSource
) : StationDictionaryRepository {

    override suspend fun getStationDictionaryList(): ApiResult<List<StationDictionaryDto>> {
        return stationRemoteDataSource.getStationDictionary()
    }
}