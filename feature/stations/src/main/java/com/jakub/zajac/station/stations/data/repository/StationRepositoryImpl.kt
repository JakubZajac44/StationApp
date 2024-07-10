package com.jakub.zajac.station.stations.data.repository

import android.util.Log
import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.local.data_source.StationLocalDataSource
import com.jakub.zajac.station.stations.data.local.model.StationEntity
import com.jakub.zajac.station.stations.data.mapper.toStationDto
import com.jakub.zajac.station.stations.data.mapper.toStationEntity
import com.jakub.zajac.station.stations.data.remote.data_source.StationRemoteDataSource
import com.jakub.zajac.station.stations.data.remote.model.StationDto
import com.jakub.zajac.station.stations.domain.repository.StationRepository
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val stationRemoteDataSource: StationRemoteDataSource,
    private val stationLocalDataSource: StationLocalDataSource
) : StationRepository {

    private val CACHE_VALID_TIME_IN_MILLISECONDS = 24 * 60 * 60 * 1000

    override suspend fun getStationList(isRefresh: Boolean): ApiResult<List<StationDto>> {
        Log.e("StationRepositoryImpl", "Start get data")
        if (isRefresh) {
            Log.e("StationRepositoryImpl", "Refresh data")
            stationLocalDataSource.clearAllStation()
            return getStationListFromRemoteServer()
        }
        val stationListCached = stationLocalDataSource.getStationList()
        Log.e("StationRepositoryImpl", "Get cached data size: ${stationListCached.size}")
        return if (stationListCached.isNotEmpty()) {
            Log.e("StationRepositoryImpl", "Cached not empty. Checking valid status timestamp")
            if (isStationDataCachedValid(stationListCached)) {
                Log.e("StationRepositoryImpl", "Cached not empty. Checking valid status: VALID")
                ApiResult.Success(stationListCached.map { it.toStationDto() })
            } else {
                Log.e("StationRepositoryImpl", "Cached not empty. Checking valid status: INVALID")
                stationLocalDataSource.clearAllStation()
                getStationListFromRemoteServer()
            }
        } else {
            Log.e("StationRepositoryImpl", "Cached empty")
            getStationListFromRemoteServer()
        }
    }

    private suspend fun getStationListFromRemoteServer(): ApiResult<List<StationDto>> {
        Log.e("StationRepositoryImpl", "Get data from server")
        return when (val apiResult = stationRemoteDataSource.getStationList()) {
            is ApiResult.Error -> apiResult
            is ApiResult.Success -> {
                val currentTime = System.currentTimeMillis()
                stationLocalDataSource.insertAllStationList(apiResult.data.map {
                    it.toStationEntity(currentTime)
                })
                Log.e("StationRepositoryImpl", "Cached new data, size: ${apiResult.data.size}")
                ApiResult.Success(apiResult.data)
            }
        }

    }

    private fun isStationDataCachedValid(stationDataCached: List<StationEntity>): Boolean {
        val currentTime = System.currentTimeMillis()
        Log.e(
            "StationRepositoryImpl",
            "Current time: $currentTime, first item time: ${stationDataCached.first().timestamp}, difference: ${(currentTime - stationDataCached.first().timestamp)}"
        )
        stationDataCached.find { (currentTime - it.timestamp) > CACHE_VALID_TIME_IN_MILLISECONDS }
            ?.let {
                return false
            } ?: run {
            return true
        }
    }
}

