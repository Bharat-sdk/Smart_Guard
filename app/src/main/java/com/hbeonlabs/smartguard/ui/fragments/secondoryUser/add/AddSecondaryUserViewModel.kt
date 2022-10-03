package com.hbeonlabs.smartguard.ui.fragments.secondoryUser.add

import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.data.local.repo.SecondaryUserRepositoryImp
import com.hbeonlabs.smartguard.ui.fragments.secondoryUser.SecondaryUserEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddSecondaryUserViewModel @Inject constructor(
    private val repo: SecondaryUserRepositoryImp
):BaseViewModel()
{
    private val _addSecondaryUserEvents = MutableSharedFlow<AddSecondaryUserEvents>()
    val addSecondaryUserEvents: SharedFlow<AddSecondaryUserEvents> = _addSecondaryUserEvents

    fun addSecondaryUser(secondaryUser:SecondaryUser)
    {
        viewModelScope.launch {
            if (secondaryUser.user_name.isBlank() || secondaryUser.user_pic.isBlank() || secondaryUser.user_phone_number.isBlank())
            {
                _addSecondaryUserEvents.emit(AddSecondaryUserEvents.SQLErrorEvent("Please Fill All the Fields"))
            }
            try
            {
                repo.addSecondaryUser(secondaryUser )
                _addSecondaryUserEvents.emit(AddSecondaryUserEvents.AddUserSuccessEvent)
            }
            catch (e: Exception) {
                _addSecondaryUserEvents.emit(AddSecondaryUserEvents.SQLErrorEvent(e.localizedMessage))
            }
        }
    }

}

sealed class AddSecondaryUserEvents{
    class SQLErrorEvent(val message: String):AddSecondaryUserEvents()
    object AddUserSuccessEvent:AddSecondaryUserEvents()
}