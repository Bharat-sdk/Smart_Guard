package com.hbeonlabs.smartguard.data.local.repo

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.datastore.SharedPreferences
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.room.HubDao
import kotlinx.coroutines.flow.Flow

class SensorRepositoryImp constructor(
    private val dao: HubDao
    ) : SensorRepository {

    override suspend fun getAllSensors(hub_serial_no: String): Flow<List<Sensor>> = dao.getAllSensorUsingHubId(hub_serial_no)
    override suspend fun deleteSensor(sensor: Sensor): Int {
      return  dao.deleteSensor(sensor)
    }

    override suspend fun addSensor(sensor: Sensor) {
        dao.addSensor(sensor)
    }

    override suspend fun editSensor(sensor: Sensor): Int {
       return dao.editSensor(sensor)
    }

    override suspend fun getAllSensorsList(hub_serial_no: String): Array<Sensor> {
        return dao.getAllSensorListHubId(hub_serial_no)
    }


}

