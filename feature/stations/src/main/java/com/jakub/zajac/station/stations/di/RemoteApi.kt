package com.jakub.zajac.station.stations.di

import com.jakub.zajac.station.stations.data.remote.api.StationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteApi {

    @Provides
    @Singleton
    fun provideStationApi(retrofit: Retrofit): StationApi = retrofit.create(
        StationApi::class.java
    )
}