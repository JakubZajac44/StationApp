package com.jakub.zajac.station.stations.di

import com.jakub.zajac.station.stations.data.repository.StationDictionaryRepositoryImpl
import com.jakub.zajac.station.stations.data.repository.StationRepositoryImpl
import com.jakub.zajac.station.stations.domain.repository.StationDictionaryRepository
import com.jakub.zajac.station.stations.domain.repository.StationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideStationRepository(
        stationRepositoryImpl: StationRepositoryImpl
    ): StationRepository

    @Binds
    @Singleton
    abstract fun provideStationDictionaryRepository(
        stationDictionaryRepositoryImpl: StationDictionaryRepositoryImpl
    ): StationDictionaryRepository
}