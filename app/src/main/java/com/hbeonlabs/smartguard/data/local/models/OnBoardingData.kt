package com.hbeonlabs.smartguard.data.local.models

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes

data class OnBoardingData(
    val title:String,
    val description:String,
    val image:Drawable,
    val backgroundColor: Int
)
