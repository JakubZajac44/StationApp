package com.jakub.zajac.station.stations.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jakub.zajac.station.stations.data.local.dao.StationDao
import com.jakub.zajac.station.stations.data.local.dao.StationDictionaryDao
import com.jakub.zajac.station.stations.data.local.model.StationDictionaryEntity
import com.jakub.zajac.station.stations.data.local.model.StationEntity

@Database(
    entities = [StationEntity::class, StationDictionaryEntity:: class],
    version = 1
)
abstract class StationDataBase : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun stationDictionaryDao(): StationDictionaryDao

    companion object {

        fun getDatabase(context: Context): StationDataBase {
            return Room.databaseBuilder(
                    context.applicationContext,
                    StationDataBase::class.java,
                    "station_db"
                ).build()
        }
    }
}
