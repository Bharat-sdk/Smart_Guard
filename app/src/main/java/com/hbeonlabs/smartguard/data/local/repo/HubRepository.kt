package com.hbeonlabs.smartguard.data.local.repo

import android.graphics.Bitmap
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName
import kotlinx.coroutines.flow.Flow

interface HubRepository {
    suspend fun getAllHubs(): Flow<List<Hub>>

    suspend fun addHub(hubSerialNo:String, hubPhoneNumber:String)

    suspend fun checkIfHubAlreadyAdded(hubSerialNo:String):Boolean

    suspend fun addImageAndNameToHub(hub_name:String, hubImage: Bitmap, hub_serial_no: String)
}