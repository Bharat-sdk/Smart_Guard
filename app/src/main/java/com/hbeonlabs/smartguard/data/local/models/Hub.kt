package com.hbeonlabs.smartguard.data.local.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "hub")
data class Hub
    (
   @PrimaryKey
   val hub_serial_number:String,
   val hub_name :String,
   val hub_image:String,
   val hub_phone_number : String,
   val hub_siren : Boolean,
   val hub_arm_state : Boolean,

   ):Serializable
