package com.hbeonlabs.smartguard.data.local.room

import android.graphics.Bitmap
import androidx.room.*
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.UpdateHubImageName
import com.hbeonlabs.smartguard.data.local.room.relations.HubWithSensors
import kotlinx.coroutines.flow.Flow

@Dao
interface HubDao {

    @Query("SELECT * FROM hub")
    fun getAllHubsList(): Flow<List<Hub>>

    @Query("UPDATE hub SET hub_arm_state = :armDisarmState WHERE hub_serial_number in(:hub_id)")
    suspend fun armDisarmHub(armDisarmState:Boolean,hub_id:String)

    @Query("UPDATE hub SET hub_siren = :sirenRingState WHERE hub_serial_number in(:hub_id)")
    suspend fun sirenRingHub(sirenRingState:Boolean,hub_id:String)

    @Insert
    suspend fun addHub(hub:Hub)

    @Query("SELECT EXISTS(SELECT * FROM hub WHERE hub_serial_number = :hub_serial_no)")
    suspend fun checkIfHubAlreadyPresent(hub_serial_no:String):Boolean

    @Query("UPDATE hub SET hub_name = :hub_name , hub_image = :hubImage WHERE hub_serial_number in(:hub_serial_no)")
    suspend fun addUpdateHub(hub_name:String ,hubImage:String ,hub_serial_no:String)

    @Transaction
    @Query("SELECT * FROM hub WHERE hub_serial_number = :hub_id")
    fun getAllSensorUsingHubId(hub_id: String):Flow<List<HubWithSensors>>

}