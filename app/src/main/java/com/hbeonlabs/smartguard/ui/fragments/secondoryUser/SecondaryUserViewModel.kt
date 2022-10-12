package com.hbeonlabs.smartguard.ui.fragments.secondoryUser

import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import com.hbeonlabs.smartguard.data.local.repo.SecondaryUserRepositoryImp
import com.hbeonlabs.smartguard.ui.fragments.hubSettings.HubSettingEvents
import com.hbeonlabs.smartguard.ui.fragments.secondoryUser.add.AddSecondaryUserEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondaryUserViewModel  @Inject constructor(
    private val repo: SecondaryUserRepositoryImp
):BaseViewModel()
{
    private val _secondaryUserEvents = MutableSharedFlow<SecondaryUserEvents>()
    val secondaryUserEvents: SharedFlow<SecondaryUserEvents> = _secondaryUserEvents

    var hubId = ""

    suspend fun getSecondaryUsersUsingHub(hubId:String):Flow<List<SecondaryUser>> = repo.getAllSecondaryUserByHub(hubId)

    fun deleteSecondaryUser(secondaryUser: SecondaryUser) {
        viewModelScope.launch(Dispatchers.IO) {
            try
            {
                repo.deleteSecondaryUser(secondaryUser )
                _secondaryUserEvents.emit(SecondaryUserEvents.DeleteSecondaryUserSuccessEvent)
            }
            catch (e: Exception) {
                _secondaryUserEvents.emit(SecondaryUserEvents.SQLErrorEvent(e.localizedMessage))
            }
        }
    }


}

sealed class SecondaryUserEvents{
    class SQLErrorEvent(val message: String):SecondaryUserEvents()
    object DeleteSecondaryUserSuccessEvent:SecondaryUserEvents()
}