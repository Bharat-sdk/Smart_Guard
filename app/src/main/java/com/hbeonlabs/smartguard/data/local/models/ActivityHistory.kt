package com.hbeonlabs.smartguard.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityHistory(
    @PrimaryKey
    val activity_history_time_stamp: Long,
    val activity_history_message: String,
    val hub_serial_number:String
)
