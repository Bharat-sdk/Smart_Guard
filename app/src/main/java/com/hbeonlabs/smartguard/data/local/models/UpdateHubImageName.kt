package com.hbeonlabs.smartguard.data.local.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UpdateHubImageName(
    @PrimaryKey
    val hub_serial_no:String,
    val hub_name :String,
    val hub_image:Bitmap,
)
