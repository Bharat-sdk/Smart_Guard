package com.hbeonlabs.smartguard.data.local.repo

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.data.local.datastore.SharedPreferences
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.room.HubDao
import kotlinx.coroutines.flow.Flow

class SecondaryUserRepositoryImp constructor(
    private val dao: HubDao
    ) : SecondaryUserRepository {


    override suspend fun getAllSecondaryUserByHub(hub_serial_no: String): Flow<List<SecondaryUser>> = dao.getSecondaryUsersUsingHub(hub_serial_no)
    override suspend fun addSecondaryUser(secondaryUser: SecondaryUser) {
        dao.addSecondaryUser(secondaryUser)
    }

    override suspend fun editSecondaryUser(secondaryUser: SecondaryUser): Int {
        return dao.updateSecondaryUsers(secondaryUser)
    }


}

