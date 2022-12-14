package com.hbeonlabs.smartguard.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hbeonlabs.smartguard.data.local.models.*

@Database(
    entities = [
        Hub::class,
        Sensor::class,
        ActivityHistory::class,
        SecondaryUser::class
    ],
    version =  5
)
@TypeConverters(Converters::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun getDao():HubDao
}