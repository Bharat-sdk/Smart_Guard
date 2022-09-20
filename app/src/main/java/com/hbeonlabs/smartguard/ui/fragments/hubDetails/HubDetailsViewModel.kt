package com.hbeonlabs.smartguard.ui.fragments.hubDetails
import android.icu.util.Calendar
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.repo.HubRepository
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class HubDetailsViewModel constructor(
    private val repository: HubRepositoryImp
):BaseViewModel() {

    private val _progressIndicator = MutableStateFlow<Int>(0)
    val progressIndicatorLiveData: StateFlow<Int> = _progressIndicator

    private val _hubEvents = MutableSharedFlow<HubDetailsEvents>()
    val hubEvents: SharedFlow<HubDetailsEvents> = _hubEvents

    var hub_id = ""

    var job: Job = viewModelScope.launch { }

    fun startPress() {
        job = viewModelScope.launch {
            for (i in 0..100) {
                delay(10)
                _progressIndicator.emit(i)
            }
        }
    }

    fun resetPress() {
        job.cancel()
        viewModelScope.launch {
            _progressIndicator.emit(0)

        }
    }

    fun armDisarmHub(armState:Boolean){
        viewModelScope.launch {
            try {
                repository.armDisarmHub(armState,hub_id)
                val date = Date()
                val timeInMills = date.time
                if (armState)
                    {
                        repository.addActivityHistory(hub_id,"Hub Armed",timeInMills)
                        _hubEvents.emit(HubDetailsEvents.ArmDisarmEvent("Your hub is Armed"))

                    }
                else{
                    repository.addActivityHistory(hub_id,"Hub Disarmed",timeInMills)
                    _hubEvents.emit(HubDetailsEvents.ArmDisarmEvent("Your hub is Disarmed"))
                    }
            }
            catch (e:Exception)
            {
                _hubEvents.emit(HubDetailsEvents.SQLErrorEvent(e.localizedMessage))
            }
        }
    }

    fun silenceRingHub(silenceRingState:Boolean){
        viewModelScope.launch {
            try {
                repository.silenceRingHub(silenceRingState,hub_id)
                val date = Date()
                val timeInMills = date.time
                if (silenceRingState)
                {
                    repository.addActivityHistory(hub_id,"Siren Enabled",timeInMills)
                    _hubEvents.emit(HubDetailsEvents.SilenceRingEvent("Hub siren is Enabled"))

                }
                else{
                    repository.addActivityHistory(hub_id,"Siren Disabled",timeInMills)
                    _hubEvents.emit(HubDetailsEvents.SilenceRingEvent("Hub siren is Disabled"))
                }
            }
            catch (e:Exception)
            {
                _hubEvents.emit(HubDetailsEvents.SQLErrorEvent(e.localizedMessage))
            }
        }
    }

    suspend fun getActivityHistory() :Flow<List<ActivityHistory>> = repository.getActivityHistory(hub_id)

    sealed class HubDetailsEvents{
        class SilenceRingEvent(val message:String):HubDetailsEvents()
        class ArmDisarmEvent(val message: String):HubDetailsEvents()
        class SQLErrorEvent(val message: String):HubDetailsEvents()
    }
}