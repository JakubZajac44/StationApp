package com.jakub.zajac.station.stations.data.remote.api

import com.jakub.zajac.station.stations.data.remote.model.StationDictionaryDto
import com.jakub.zajac.station.stations.data.remote.model.StationDto
import retrofit2.Response
import retrofit2.http.GET

interface StationApi {

    @GET("stations")
    suspend fun getStation(
    ): Response<List<StationDto>>

    @GET("station_keywords")
    suspend fun getStationDictionary(
    ): Response<List<StationDictionaryDto>>

}