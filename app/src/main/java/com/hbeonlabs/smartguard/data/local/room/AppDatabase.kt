package com.hbeonlabs.smartguard.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName

@Database(
    entities = [
        Hub::class,
        Sensor::class,
        ActivityHistory::class
    ],
    version =  3
)
@TypeConverters(Converters::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun getDao():HubDao
}