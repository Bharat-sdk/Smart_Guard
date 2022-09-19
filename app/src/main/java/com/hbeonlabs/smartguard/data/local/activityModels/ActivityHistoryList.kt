package com.hbeonlabs.smartguard.data.local.activityModels

import androidx.room.Entity
import androidx.room.PrimaryKey


data class ActivityHistoryList(
    val activity_history_time_stamp: Long,
    val activity_history_message: String,
    val hub_id:String,
) : ActivityHistoryItem(TYPE_ACTIVITY_HISTORY)