package com.hbeonlabs.smartguard.data.local.repo

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.datastore.SharedPreferences
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.room.HubDao
import kotlinx.coroutines.flow.Flow
import java.util.*

class HubRepositoryImp constructor(
    private val dao: HubDao,
    private val context: Context
) : HubRepository {

    override suspend fun getAllHubs(): Flow<List<Hub>> = dao.getAllHubsList()
    override suspend fun addHub(hubSerialNo:String, hubPhoneNumber:String) {
        val currentDate = Calendar.getInstance().timeInMillis
        val hub = Hub(hubSerialNo,"", "",hubPhoneNumber, hub_siren = false, hub_arm_state = false,currentDate.toString())
        dao.addHub(hub)
    }
    override suspend fun checkIfHubAlreadyAdded(hubSerialNo: String): Boolean {
        return dao.checkIfHubAlreadyPresent(hubSerialNo)
    }

    override suspend fun addImageAndNameToHub(hub_name:String, hubImage: String, hub_serial_no: String) {
        dao.addUpdateHub(hub_name,hubImage,hub_serial_no)
    }

    override suspend fun silenceRingHub(ring: Boolean,hub_serial_no: String) {
        dao.sirenRingHub(ring,hub_serial_no)
    }

    override suspend fun armDisarmHub(arm_state: Boolean, hub_serial_no: String) {
        dao.armDisarmHub(arm_state,hub_serial_no)
    }

    override suspend fun addActivityHistory(
        hub_serial_no: String,
        message: String,
        timeStamp: Long
    ) {
        dao.addActivityHistory(ActivityHistory(timeStamp,message,hub_serial_no))
    }

    override suspend fun getHubDetails(hub_serial_no: String): Flow<Hub> {
        return dao.getHubFromId(hub_serial_no)
    }

    override suspend fun getActivityHistory(hub_serial_no: String): Flow<List<ActivityHistory>> {
        return dao.getAllActivities(hub_serial_no)
    }

    override suspend fun getHubFromId(hub_serial_no: String): Flow<Hub> {
        return dao.getHubFromId(hub_serial_no)
    }




}

