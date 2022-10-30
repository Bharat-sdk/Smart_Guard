package com.hbeonlabs.smartguard.ui.fragments.activityHistory
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ActivityHistoryViewModel @Inject constructor(
    private val repository: HubRepositoryImp
):BaseViewModel()
{
    var hub_id = ""
    suspend fun getActivityHistory() : Flow<List<ActivityHistory>> = repository.getActivityHistory(hub_id)

}