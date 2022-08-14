package com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PagerSOSViewModel:BaseViewModel()
{
    private val _progressIndicator= MutableStateFlow<Int>(0)
    val progressIndicatorLiveData: StateFlow<Int> = _progressIndicator

    lateinit var job :Job

    fun startPress() {
        job =  viewModelScope.launch{
            for(i in 0..100)
            {
                delay(10)
                _progressIndicator.emit(i)
            }
        }
    }


    fun resetPress(){
        job.cancel()
         viewModelScope.launch {
            _progressIndicator.emit(0)

        }

    }



}