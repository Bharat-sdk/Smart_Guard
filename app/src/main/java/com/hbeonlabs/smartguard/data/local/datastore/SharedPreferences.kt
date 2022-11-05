package com.hbeonlabs.smartguard.data.local.datastore

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.hbeonlabs.smartguard.utils.AppConstants


class SharedPreferences constructor(
    private val context: Context
) {

    val sharedPreferences = context.getSharedPreferences(shared_preferences, MODE_PRIVATE)
    val editor = sharedPreferences.edit()

     fun isFirstTime():Boolean{
       return sharedPreferences.getBoolean(AppConstants.isFirstTime,true)
    }


    fun setIsFirstTime(isFirstTime:Boolean)
    {
        editor.putBoolean(AppConstants.isFirstTime,isFirstTime)
        editor.commit()
    }




    companion object {
        private const val shared_preferences = "app_preferences"
    }
}