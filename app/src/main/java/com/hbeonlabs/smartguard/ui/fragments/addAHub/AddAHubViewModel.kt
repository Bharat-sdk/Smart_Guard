package com.hbeonlabs.smartguard.ui.fragments.addAHub
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AddAHubViewModel constructor(
private val hubRepositoryImp: HubRepositoryImp
):BaseViewModel()
{
    private val _addHubEvents = MutableSharedFlow<AddAHubEvent>()
    val addHubEvents: SharedFlow<AddAHubEvent> = _addHubEvents

    fun addHub(hubSerialNo:String, hubPhoneNumber:String)
    {

        viewModelScope.launch {
            if (hubSerialNo.isEmpty()){
                _addHubEvents.emit(AddAHubEvent.SerialNumberValidationErrorEvent("Please Enter Hub Serial Number"))
            }
            else if(hubPhoneNumber.isEmpty())
            {
                _addHubEvents.emit(AddAHubEvent.PhoneValidationErrorEvent("Please Enter Your Phone Number"))
            }
            else if (hubPhoneNumber.length < 10)
            {
                _addHubEvents.emit(AddAHubEvent.PhoneValidationErrorEvent("Please Enter Valid Phone Number"))
            }
            else{
                try {
                    if (hubRepositoryImp.checkIfHubAlreadyAdded(hubSerialNo))
                    {
                        _addHubEvents.emit(AddAHubEvent.SQLErrorEvent("This Hub is Already Added"))
                    }
                    else{
                        hubRepositoryImp.addHub(hubSerialNo,hubPhoneNumber)
                        _addHubEvents.emit(AddAHubEvent.NavigateToPostHubEvent)
                    }
                }
                catch (e:Exception)
                {
                    _addHubEvents.emit(AddAHubEvent.SQLErrorEvent(e.localizedMessage))
                }

            }

        }
    }
}

sealed class AddAHubEvent{
    object NavigateToPostHubEvent:AddAHubEvent()
    class SerialNumberValidationErrorEvent(val message:String):AddAHubEvent()
    class PhoneValidationErrorEvent(val message: String):AddAHubEvent()
    class SQLErrorEvent(val message: String):AddAHubEvent()


}