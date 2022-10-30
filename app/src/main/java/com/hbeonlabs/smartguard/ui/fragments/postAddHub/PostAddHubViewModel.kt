package com.hbeonlabs.smartguard.ui.fragments.postAddHub

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class PostAddHubViewModel constructor(
    private val hubRepositoryImp: HubRepositoryImp
):BaseViewModel()
{
    private val _postAddHubEvents = MutableSharedFlow<AddHubEvents>()
    val postAddHubEvents: SharedFlow<AddHubEvents> = _postAddHubEvents

    fun updateHub( hubName:String, hubImage:String,hubSerialNo:String)
    {
        viewModelScope.launch {
            if (hubName.isEmpty()){
                _postAddHubEvents.emit(AddHubEvents.HubNameValidationErrorEvent("Please Enter A Name For The Hub"))
            }
            else if (hubImage.isNullOrEmpty())
            {
                _postAddHubEvents.emit(AddHubEvents.HubNameValidationErrorEvent("Please Select A Image For The Hub"))

            }
            else{
                try {
                        hubRepositoryImp.addImageAndNameToHub(hubName,hubImage,hubSerialNo)
                        _postAddHubEvents.emit(AddHubEvents.NavigateToHub)
                    }
                catch (e:Exception)
                {
                    _postAddHubEvents.emit(AddHubEvents.SQLErrorEvent(e.localizedMessage))
                }
            }
        }
    }
}

sealed class AddHubEvents{
    object NavigateToHub:AddHubEvents()
    class HubNameValidationErrorEvent(val message:String):AddHubEvents()
    class SQLErrorEvent(val message: String):AddHubEvents()
}