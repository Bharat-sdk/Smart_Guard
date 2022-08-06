package com.hbeonlabs.smartguard.ui.fragments.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SplashviewModel:BaseViewModel(){
    private val _splashEvent = MutableSharedFlow<SplashEvent>()
    val splashEvent: SharedFlow<SplashEvent> = _splashEvent

    fun splashToHome()
    {
        viewModelScope.launch {
            delay(1000)
            _splashEvent.emit(SplashEvent.NavigateToOnBoardingEvent)
            Log.d("TAG", "splashToHome: ")
        }
    }
}

sealed class SplashEvent {
    object NavigateToOnBoardingEvent : SplashEvent()
    object NavigateToHubEvent : SplashEvent()
}
