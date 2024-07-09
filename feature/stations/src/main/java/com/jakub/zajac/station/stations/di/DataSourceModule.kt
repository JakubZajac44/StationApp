package com.jakub.zajac.station.stations.di

import com.jakub.zajac.station.stations.data.local.dao.StationDao
import com.jakub.zajac.station.stations.data.local.dao.StationDictionaryDao
import com.jakub.zajac.station.stations.data.local.data_source.StationLocalDataSource
import com.jakub.zajac.station.stations.data.remote.api.StationApi
import com.jakub.zajac.station.stations.data.remote.data_source.StationRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideStationRemoteDataSource(api: StationApi): StationRemoteDataSource =
        StationRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideStationDictionaryRemoteDataSource(
        stationDao: StationDao,
        stationDictionaryDao: StationDictionaryDao
    ): StationLocalDataSource =
        StationLocalDataSource(stationDao = stationDao, stationDictionaryDao = stationDictionaryDao)
}