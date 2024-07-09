package com.jakub.zajac.station.stations.di

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
    fun provideCharacterRemoteDataSource(api: StationApi): StationRemoteDataSource =
        StationRemoteDataSource(api)
}