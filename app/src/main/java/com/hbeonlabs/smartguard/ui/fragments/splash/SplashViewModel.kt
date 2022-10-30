package com.hbeonlabs.smartguard.ui.fragments.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.datastore.SharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SplashViewModel constructor(
    private val sharedPreferences: SharedPreferences
):BaseViewModel(){
    private val _splashEvent = MutableSharedFlow<SplashEvent>()
    val splashEvent: SharedFlow<SplashEvent> = _splashEvent

    fun splashToHome()
    {
        viewModelScope.launch {
            delay(1000)
            Log.d("TAG", "splashToHome: "+sharedPreferences.isFirstTime())
            if (sharedPreferences.isFirstTime()) {
                _splashEvent.emit(SplashEvent.NavigateToOnBoardingEvent)
            }
            else{
                _splashEvent.emit(SplashEvent.NavigateToHubEvent)
            }
        }
    }

    fun changeIsFirstLoggedIn(){
        viewModelScope.launch {
            sharedPreferences.setIsFirstTime(false)
            Log.d("TAG", "splashToHome: "+sharedPreferences.isFirstTime())
        }
    }
}

sealed class SplashEvent {
    object NavigateToOnBoardingEvent : SplashEvent()
    object NavigateToHubEvent : SplashEvent()
}
