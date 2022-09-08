package com.hbeonlabs.smartguard.data.local.repo

import com.hbeonlabs.smartguard.data.local.models.Hub
import kotlinx.coroutines.flow.Flow

interface HubRepository {
    suspend fun getAllHubs(): Flow<List<Hub>>

    suspend fun addHub(hub: Hub)

    suspend fun checkIfHubAlreadyAdded(hubSerialNo:String):Boolean
}