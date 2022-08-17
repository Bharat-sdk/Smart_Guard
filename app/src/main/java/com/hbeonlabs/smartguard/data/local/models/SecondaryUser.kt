package com.hbeonlabs.smartguard.data.local.models

import androidx.annotation.StringRes

data class SecondaryUser(
    val user_id: Long,
    val user_name: String,
    val user_pic:String,
    val user_phone_number: String,
    val hub_id: String
    )