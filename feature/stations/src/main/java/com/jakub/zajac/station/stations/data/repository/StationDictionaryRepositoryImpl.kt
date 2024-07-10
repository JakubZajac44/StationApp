package com.jakub.zajac.station.stations.data.repository

import android.util.Log
import com.jakub.zajac.station.resource.ApiResult
import com.jakub.zajac.station.stations.data.local.data_source.StationLocalDataSource
import com.jakub.zajac.station.stations.data.local.model.StationDictionaryEntity
import com.jakub.zajac.station.stations.data.mapper.toStationDictionaryDto
import com.jakub.zajac.station.stations.data.mapper.toStationDictionaryEntity
import com.jakub.zajac.station.stations.data.remote.data_source.StationRemoteDataSource
import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto
import com.jakub.zajac.station.stations.domain.repository.StationDictionaryRepository
import javax.inject.Inject

class StationDictionaryRepositoryImpl @Inject constructor(
    private val stationRemoteDataSource: StationRemoteDataSource,
    private val stationLocalDataSource: StationLocalDataSource
) : StationDictionaryRepository {

    private val CACHE_VALID_TIME_IN_MILLISECONDS = 24 * 60 * 60 * 1000

    override suspend fun getStationDictionaryList(isRefresh: Boolean): ApiResult<List<StationDictionaryDto>> {
        Log.e("StationDictionaryRepository", "Start get data")
        if (isRefresh) {
            Log.e("StationDictionaryRepository", "Refresh data")
            stationLocalDataSource.clearAllStationDictionary()
            return getStationDictionaryListFromRemoteServer()
        }
        val stationDictionaryListCached = stationLocalDataSource.getStationDictionaryList()
        Log.e(
            "StationDictionaryRepository",
            "Get cached data size: ${stationDictionaryListCached.size}"
        )
        return if (stationDictionaryListCached.isNotEmpty()) {
            Log.e(
                "StationDictionaryRepository",
                "Cached not empty. Checking valid status timestamp"
            )
            if (isStationDataCachedValid(stationDictionaryListCached)) {
                Log.e(
                    "StationDictionaryRepository",
                    "Cached not empty. Checking valid status: VALID"
                )
                ApiResult.Success(stationDictionaryListCached.map { it.toStationDictionaryDto() })
            } else {
                Log.e(
                    "StationDictionaryRepository",
                    "Cached not empty. Checking valid status: INVALID"
                )
                stationLocalDataSource.clearAllStationDictionary()
                getStationDictionaryListFromRemoteServer()
            }
        } else {
            Log.e("StationDictionaryRepository", "Cached empty")
            getStationDictionaryListFromRemoteServer()
        }
    }


    private suspend fun getStationDictionaryListFromRemoteServer(): ApiResult<List<StationDictionaryDto>> {
        Log.e("StationDictionaryRepository", "Get data from server")
        return when (val apiResult = stationRemoteDataSource.getStationDictionary()) {
            is ApiResult.Error -> apiResult
            is ApiResult.Success -> {
                val currentTime = System.currentTimeMillis()
                stationLocalDataSource.insertAllStationDictionaryList(apiResult.data.map {
                    it.toStationDictionaryEntity(currentTime)
                })
                Log.e(
                    "StationDictionaryRepository",
                    "Cached new data, size: ${apiResult.data.size}"
                )
                ApiResult.Success(apiResult.data)
            }
        }

    }

    private fun isStationDataCachedValid(stationDataCached: List<StationDictionaryEntity>): Boolean {
        val currentTime = System.currentTimeMillis()
        Log.e(
            "StationDictionaryRepository",
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