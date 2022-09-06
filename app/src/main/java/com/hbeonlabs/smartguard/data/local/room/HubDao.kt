package com.hbeonlabs.smartguard.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.room.relations.HubWithSensors
import kotlinx.coroutines.flow.Flow

@Dao
interface HubDao {

    @Query("SELECT * FROM hub")
    suspend fun getAllHubsList(): Flow<List<Hub>>

    @Query("UPDATE hub SET hub_arm_state = :armDisarmState WHERE hub_id = :hub_id")
    suspend fun armDisarmHub(armDisarmState:Boolean,hub_id:String)

    @Query("UPDATE hub SET hub_siren = :sirenRingState WHERE hub_id = :hub_id")
    suspend fun sirenRingHub(sirenRingState:Boolean,hub_id:String)

    @Insert
    suspend fun addHub(hub:Hub)

    @Query("UPDATE hub SET hub_name = :name , hub_image = :imageUrl WHERE hub_id = :hub_id")
    suspend fun addUpdateHub(name:String, imageUrl:String,hub_id:String)

    @Transaction
    @Query("SELECT * FROM hub WHERE hub_id = :hub_id")
    suspend fun getAllSensorUsingHubId(hub_id: String):List<HubWithSensors>
}