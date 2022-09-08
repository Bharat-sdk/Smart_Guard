package com.hbeonlabs.smartguard.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor

@Database(
    entities = [
        Hub::class,
        Sensor::class
    ],
    version = 1
)
abstract class AppDatabase :RoomDatabase(){
    abstract fun getDao():HubDao
}