package com.hbeonlabs.smartguard.ui.fragments.addAHub
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.sql.SQLException

class AddAHubViewModel constructor(
private val hubRepositoryImp: HubRepositoryImp
):BaseViewModel()
{
    private val _addHubEvents = MutableSharedFlow<AddHubEvents>()
    val addHubEvents: SharedFlow<AddHubEvents> = _addHubEvents

    fun addHub(hubSerialNo:String, hubPhoneNumber:String)
    {

        viewModelScope.launch {
            if (hubSerialNo.isEmpty()){
                _addHubEvents.emit(AddHubEvents.SerialNumberValidationErrorEvent("Please Enter Hub Serial Number"))
            }
            else if(hubPhoneNumber.isEmpty())
            {
                _addHubEvents.emit(AddHubEvents.PhoneValidationErrorEvent("Please Enter Your Phone Number"))
            }
            else if (hubPhoneNumber.length < 10)
            {
                _addHubEvents.emit(AddHubEvents.PhoneValidationErrorEvent("Please Enter Valid Phone Number"))
            }
            else{
                try {
                    if (hubRepositoryImp.checkIfHubAlreadyAdded(hubSerialNo))
                    {
                        _addHubEvents.emit(AddHubEvents.SQLErrorEvent("This Hub is Already Added"))
                    }
                    else{
                        hubRepositoryImp.addHub(hubSerialNo,hubPhoneNumber)
                        _addHubEvents.emit(AddHubEvents.NavigateToPostHubEvent)
                    }
                }
                catch (e:Exception)
                {
                    _addHubEvents.emit(AddHubEvents.SQLErrorEvent(e.localizedMessage))
                }

            }

        }
    }
}

sealed class AddHubEvents{
    object NavigateToPostHubEvent:AddHubEvents()
    class SerialNumberValidationErrorEvent(val message:String):AddHubEvents()
    class PhoneValidationErrorEvent(val message: String):AddHubEvents()
    class SQLErrorEvent(val message: String):AddHubEvents()


}