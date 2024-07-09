package com.jakub.zajac.station.stations.data.repository

import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.local.data_source.StationLocalDataSource
import com.jakub.zajac.station.stations.data.remote.data_source.StationRemoteDataSource
import com.jakub.zajac.station.stations.data.remote.model.StationDto
import com.jakub.zajac.station.stations.domain.repository.StationRepository
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val stationRemoteDataSource: StationRemoteDataSource,
    private val stationLocalDataSource: StationLocalDataSource
) : StationRepository {
    override suspend fun getStationList(): ApiResult<List<StationDto>> {

        return stationRemoteDataSource.getStationList()
    }
}