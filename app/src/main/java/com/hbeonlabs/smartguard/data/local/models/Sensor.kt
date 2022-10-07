package com.hbeonlabs.smartguard.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "sensor")
data class Sensor(
    @PrimaryKey(autoGenerate = true)
    val sensor_id: Long? = null,
    val sensor_name: String,
    val sensor_image:String,
    val sensor_type:String,
    val sensor_arm_state:Boolean,
    val sensor_custom_sms: String,
    val sensor_registered_on:String,
    val hub_serial_number: String
):Serializable
