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
   val hub_registered_on:String,
   val hub_arm_registered:Boolean ,
   val hub_disarm_registered:Boolean ,
   val hub_silence_registered:Boolean ,
   val hub_ring_registered:Boolean ,

   ):Serializable
