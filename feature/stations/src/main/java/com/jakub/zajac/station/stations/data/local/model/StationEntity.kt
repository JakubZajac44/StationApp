package com.jakub.zajac.station.stations.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val hits: Int,
)