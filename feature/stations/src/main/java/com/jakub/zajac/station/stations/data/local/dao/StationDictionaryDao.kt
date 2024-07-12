package com.jakub.zajac.station.stations.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jakub.zajac.station.stations.data.local.model.StationDictionaryEntity


@Dao
interface StationDictionaryDao {

    @Upsert
    suspend fun upsertAll(stationDictionaryList: List<StationDictionaryEntity>)

    @Query("Select * from StationDictionaryEntity")
    suspend fun getAllDictionaryStation(): List<StationDictionaryEntity>

    @Query("DELETE FROM StationDictionaryEntity")
    suspend fun clearAll()
}