package com.hbeonlabs.smartguard.data.local.models

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SecondaryUser(
    @PrimaryKey (autoGenerate = true)
    val user_id: Long?=null,
    val user_name: String,
    val slot:Int,
    val user_pic:String,
    val user_phone_number: String,
    val hub_serial_number: String
    ):Serializable