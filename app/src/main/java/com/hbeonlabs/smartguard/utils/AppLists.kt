package com.hbeonlabs.smartguard.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryList
import com.hbeonlabs.smartguard.data.local.models.OnBoardingData
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.data.local.models.SensorTypes

class AppLists(
    val context: Context
) {

    val activityList = arrayListOf<ActivityHistoryList>(
        ActivityHistoryList(1660453309476,"One","1"),
        ActivityHistoryList(1660453067070,"2","1"),
        ActivityHistoryList(1660433067070,"3","1"),
        ActivityHistoryList(1661433067070,"4","1"),
        ActivityHistoryList(1651433067070,"5","1"),
        ActivityHistoryList(1651433067070,"55","1"),
        ActivityHistoryList(1651433067070,"555","1"),
    )

    val fragmentList = arrayListOf<OnBoardingData>(
        OnBoardingData(context.getString(R.string.onboard_title_1),context.getString(R.string.onboard_desc_1), ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.on_boarding_blue)),
        OnBoardingData(context.getString(R.string.onboard_title_2),context.getString(R.string.onboard_desc_2),ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.on_boarding_green)),
        OnBoardingData(context.getString(R.string.onboard_title_3),context.getString(R.string.onboard_desc_3), ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.on_boarding_orange)),
        OnBoardingData(context.getString(R.string.onboard_title_4),context.getString(R.string.onboard_desc_4), ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.purple_200)),
    )

    val secondaryUserList = arrayListOf(
        SecondaryUser(1,"","","",""),
        SecondaryUser(2,"Bharat","1","354855454","1"),
        SecondaryUser(3,"","","",""),
        SecondaryUser(4,"Bharat","1","354855454","1"),
        SecondaryUser(5,"Bharat","1","354855454","1")
    )

    val sensorTypeLists = arrayListOf(
        SensorTypes("2455","Sensor1","asdasd"),
        SensorTypes("2455","Sensor1","asdasd"),
        SensorTypes("2455","Sensor1","asdasd"),
        SensorTypes("2455","Sensor1","asdasd")
    )
}