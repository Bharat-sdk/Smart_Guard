package com.hbeonlabs.smartguard.data.local.activityModels

open class ActivityHistoryItem (
    val type:Int
){
    companion object{
        const val TYPE_DATE = 0
        const val TYPE_ACTIVITY_HISTORY = 1
    }
}