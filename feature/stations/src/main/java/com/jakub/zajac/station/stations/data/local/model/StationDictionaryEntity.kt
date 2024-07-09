package com.jakub.zajac.station.stations.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationDictionaryEntity(
    @PrimaryKey
    val id: Int,
    val keyword: String,
    val stationId: Int
)
