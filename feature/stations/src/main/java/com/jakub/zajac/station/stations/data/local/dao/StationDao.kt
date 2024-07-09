package com.jakub.zajac.station.stations.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jakub.zajac.station.stations.data.local.model.StationEntity

@Dao
interface StationDao {

    @Upsert
    suspend fun upsertAll(stationList: List<StationEntity>)

    @Query("Select * from StationEntity")
    suspend fun getAllStation(): List<StationEntity>

    @Query("DELETE FROM StationEntity")
    suspend fun clearAll()
}