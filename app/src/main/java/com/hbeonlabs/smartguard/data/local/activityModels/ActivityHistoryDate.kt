package com.hbeonlabs.smartguard.data.local.activityModels

data class ActivityHistoryDate(
    val activity_history_time_stamp: String,
    val size: String

) : ActivityHistoryItem(TYPE_DATE)