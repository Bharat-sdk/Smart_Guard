package com.hbeonlabs.smartguard.ui.fragments.selectHub
import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SelectHubViewModel constructor(
    private val hubRepositoryImp: HubRepositoryImp
):BaseViewModel()
{
    suspend fun getHubData():Flow<List<Hub>>
    {
       return hubRepositoryImp.getAllHubs()
    }
}

