package com.hbeonlabs.smartguard.data.local.activityModels

data class ActivityHistory(
    val activity_history_time_stamp: Long,
    val activity_history_message: String,
    val hub_id:String,
) : ActivityHistoryItem(TYPE_ACTIVITY_HISTORY)