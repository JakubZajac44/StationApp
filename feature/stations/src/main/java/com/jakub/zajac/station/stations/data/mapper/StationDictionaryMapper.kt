package com.jakub.zajac.station.stations.data.mapper

import com.jakub.zajac.station.stations.data.local.model.StationDictionaryEntity
import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto
import com.jakub.zajac.station.stations.domain.model.StationDictionaryModel

fun StationDictionaryDto.toStationDictionaryModel() = StationDictionaryModel(
    id = this.id, keyword = this.keyword, stationId = this.station_id
)

fun StationDictionaryEntity.toStationDictionaryDto() = StationDictionaryDto(
    id = this.id, keyword = this.keyword, station_id = this.stationId
)

fun StationDictionaryDto.toStationDictionaryEntity(timestamp: Long) = StationDictionaryEntity(
    id = this.id, keyword = this.keyword, stationId = this.station_id, timestamp = timestamp
)
