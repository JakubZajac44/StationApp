package com.jakub.zajac.station.stations.data.mapper

import com.jakub.zajac.station.stations.data.local.model.StationEntity
import com.jakub.zajac.station.stations.data.remote.model.StationDto
import com.jakub.zajac.station.stations.domain.model.StationModel

fun StationDto.toStationModel() = StationModel(
    id = this.id, name = this.name, hits = this.hits, longitude = this.longitude, latitude =  this.latitude
)
fun StationDto.toStationEntity(timestamp: Long) = StationEntity(
    id = this.id, name = this.name, hits = this.hits, timestamp = timestamp, latitude = latitude, longitude = longitude
)

fun StationEntity.toStationDto() = StationDto(
    id = this.id, name = this.name, hits = this.hits, latitude = latitude, longitude = longitude
)