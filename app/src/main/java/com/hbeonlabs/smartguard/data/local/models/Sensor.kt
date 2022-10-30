package com.hbeonlabs.smartguard.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensor")
data class Sensor(
    @PrimaryKey(autoGenerate = true)
    val sensor_id: Long? = null,
    val sensor_name: String,
    val sensor_image:String,
    val sensor_type:String,
    val sensor_custom_sms: String,
    val hub_serial_number: String
)
