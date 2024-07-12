package com.jakub.zajac.station.stations.data.local.data_source

import com.jakub.zajac.station.stations.data.local.dao.StationDao
import com.jakub.zajac.station.stations.data.local.dao.StationDictionaryDao
import com.jakub.zajac.station.stations.data.local.model.StationDictionaryEntity
import com.jakub.zajac.station.stations.data.local.model.StationEntity
import javax.inject.Inject

class StationLocalDataSource @Inject constructor(
    private val stationDao: StationDao, private val stationDictionaryDao: StationDictionaryDao
) {

    suspend fun getStationList() = stationDao.getAllStation()

    suspend fun getStationDictionaryList() = stationDictionaryDao.getAllDictionaryStation()

    suspend fun insertAllStationList(stationList: List<StationEntity>) {
        stationDao.upsertAll(stationList)
    }

    suspend fun insertAllStationDictionaryList(stationDictionaryList: List<StationDictionaryEntity>) {
        stationDictionaryDao.upsertAll(stationDictionaryList)
    }

    suspend fun clearAllStation() {
        stationDao.clearAll()
    }

    suspend fun clearAllStationDictionary() {
        stationDictionaryDao.clearAll()
    }

}