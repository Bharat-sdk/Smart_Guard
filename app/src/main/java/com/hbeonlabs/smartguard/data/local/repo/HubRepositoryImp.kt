package com.hbeonlabs.smartguard.data.local.repo

import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.room.HubDao
import kotlinx.coroutines.flow.Flow

class HubRepositoryImp constructor(
    private val dao: HubDao
) : HubRepository {

    override suspend fun getAllHubs(): Flow<List<Hub>> = dao.getAllHubsList()
    override suspend fun addHub(hub: Hub) = dao.addHub(hub)
    override suspend fun checkIfHubAlreadyAdded(hubSerialNo: String): Boolean {
        return dao.checkIfHubAlreadyPresent(hubSerialNo)
    }

}

