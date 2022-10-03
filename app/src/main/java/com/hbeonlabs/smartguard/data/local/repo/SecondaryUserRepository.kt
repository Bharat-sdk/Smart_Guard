package com.hbeonlabs.smartguard.data.local.repo

import android.graphics.Bitmap
import com.hbeonlabs.smartguard.data.local.models.*
import kotlinx.coroutines.flow.Flow

interface SecondaryUserRepository {
    suspend fun getAllSecondaryUserByHub(hub_serial_no:String): Flow<List<SecondaryUser>>

    suspend fun addSecondaryUser(secondaryUser: SecondaryUser)


}