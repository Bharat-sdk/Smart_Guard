package com.hbeonlabs.smartguard.data.local.repo

import android.graphics.Bitmap
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName
import kotlinx.coroutines.flow.Flow

interface HubRepository {
    suspend fun getAllHubs(): Flow<List<Hub>>

    suspend fun addHub(hubSerialNo:String, hubPhoneNumber:String)

    suspend fun checkIfHubAlreadyAdded(hubSerialNo:String):Boolean

    suspend fun addImageAndNameToHub(hub_name:String, hubImage: String, hub_serial_no: String)

    suspend fun silenceRingHub(ring:Boolean,hub_serial_no: String)

    suspend fun armDisarmHub(arm_state:Boolean,hub_serial_no: String)

    suspend fun addActivityHistory(hub_serial_no: String, message: String, timeStamp: Long)

    suspend fun getHubDetails(hub_serial_no: String):Hub

    suspend fun getActivityHistory(hub_serial_no: String):Flow<List<ActivityHistory>>


}