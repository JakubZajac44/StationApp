package com.jakub.zajac.station.stations.di

import android.content.Context
import com.jakub.zajac.station.stations.data.local.dao.StationDao
import com.jakub.zajac.station.stations.data.local.dao.StationDictionaryDao
import com.jakub.zajac.station.stations.data.local.db.StationDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Provides
    @Singleton
    fun provideStationDatabase(@ApplicationContext context: Context): StationDataBase =
        StationDataBase.getDatabase(context)

    @Provides
    @Singleton
    fun provideStationDao(database: StationDataBase): StationDao = database.stationDao()

    @Provides
    @Singleton
    fun provideStationDictionaryDao(database: StationDataBase): StationDictionaryDao =
        database.stationDictionaryDao()


}