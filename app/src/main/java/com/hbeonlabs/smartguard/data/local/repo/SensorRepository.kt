package com.hbeonlabs.smartguard.data.local.repo

import android.graphics.Bitmap
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName
import kotlinx.coroutines.flow.Flow

interface SensorRepository {
    suspend fun getAllSensors(hub_serial_no:String): Flow<List<Sensor>>

    suspend fun deleteSensor(sensor: Sensor):Int

    suspend fun addSensor(sensor: Sensor)

    suspend fun editSensor(sensor: Sensor):Int
}