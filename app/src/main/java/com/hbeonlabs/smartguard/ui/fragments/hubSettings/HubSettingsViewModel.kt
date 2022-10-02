package com.hbeonlabs.smartguard.ui.fragments.hubSettings
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import com.hbeonlabs.smartguard.ui.fragments.postAddHub.AddHubEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HubSettingsViewModel @Inject constructor(
    private val hubRepository: HubRepositoryImp
):BaseViewModel()
{
    private val _hubSettingsEvents = MutableSharedFlow<HubSettingEvents>()
    val hubSettingsEvents: SharedFlow<HubSettingEvents> = _hubSettingsEvents

    fun getHubFromId(hubId:String)
    {
        viewModelScope.launch (Dispatchers.IO){
           try {
               _hubSettingsEvents.emit(HubSettingEvents.GetHubDataEvent(hubRepository.getHubFromId(hubId)))
           }
           catch (e:Exception)
           {
               _hubSettingsEvents.emit(HubSettingEvents.SQLErrorEvent(e.localizedMessage))
           }
        }
    }

    fun updateHubName(hubName:String,hubImage:String,hub_id:String)
    {
        viewModelScope.launch (Dispatchers.IO) {
            if (hubName.isEmpty())
            {
                _hubSettingsEvents.emit(HubSettingEvents.SQLErrorEvent("Please Fill The Hub Name"))
            }
            else if (hubImage.isEmpty())
            {
                _hubSettingsEvents.emit(HubSettingEvents.SQLErrorEvent("Please Select an Image for Hub"))
            }
            else{
                try {
                    hubRepository.addImageAndNameToHub(hubName,hubImage,hub_id)
                    _hubSettingsEvents.emit(HubSettingEvents.UpdateHubSuccessEvent)
                }
                catch (e:Exception)
                {
                    _hubSettingsEvents.emit(HubSettingEvents.SQLErrorEvent(e.localizedMessage))
                }
            }
        }
    }



}

sealed class HubSettingEvents{
    class GetHubDataEvent(val hub:Hub):HubSettingEvents()
    class SQLErrorEvent(val message: String):HubSettingEvents()
    object UpdateHubSuccessEvent:HubSettingEvents()
}