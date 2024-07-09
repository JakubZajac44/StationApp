package com.jakub.zajac.station.stations.data.mapper

import com.jakub.zajac.station.stations.data.remote.model.StationDto
import com.jakub.zajac.station.stations.domain.model.StationModel

fun StationDto.toStationModel() = StationModel(
    id = this.id, name = this.name, hits = this.hits
)
