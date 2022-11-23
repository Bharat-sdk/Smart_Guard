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

    val fragmentList = arrayListOf<OnBoardingData>(
        OnBoardingData(context.getString(R.string.onboard_title_1),context.getString(R.string.onboard_desc_1), ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.on_boarding_blue)),
        OnBoardingData(context.getString(R.string.onboard_title_2),context.getString(R.string.onboard_desc_2),ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.on_boarding_green)),
        OnBoardingData(context.getString(R.string.onboard_title_3),context.getString(R.string.onboard_desc_3), ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.on_boarding_orange)),
        OnBoardingData(context.getString(R.string.onboard_title_4),context.getString(R.string.onboard_desc_4), ContextCompat.getDrawable(context,R.drawable.ic_navigate_back)!!,ContextCompat.getColor(context,R.color.purple_200)),
    )

    val secondaryUserList = arrayListOf(
        SecondaryUser(null,"",1,"","",""),
        SecondaryUser(null,"",2,"","",""),
        SecondaryUser(null,"",3,"","",""),
        SecondaryUser(null,"",4,"","",""),
        SecondaryUser(null,"",5,"","","")
    )

    val sensorTypeLists = arrayListOf(
        SensorTypes("7485","PIR Motion Sensor",R.drawable.wireless_pir_sensor),
        SensorTypes("4786","LPG Sensor",R.drawable.wireless_lpg_gas_sensor),
        SensorTypes("2455","Door Sensor",R.drawable.wireless_order_system),
        SensorTypes("4428","Smoke Sensor",R.drawable.wireless_smoke_sensor),
        SensorTypes("8956","Glass Break Sensor",R.drawable.wireless_glass_break_sensor),
        SensorTypes("8956","Water Overflow Sensor",R.drawable.wireless_glass_break_sensor),
    )

}