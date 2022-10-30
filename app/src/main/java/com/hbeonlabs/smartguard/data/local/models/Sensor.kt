package com.hbeonlabs.smartguard.data.local.models

data class Sensor(
    val sensor_id: Long,
    val sensor_name: String,
    val sensor_image:String,
    val sensor_type:String,
    val sensor_custom_sms: String,
    val hub_id: String
)
