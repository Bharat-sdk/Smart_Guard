package com.hbeonlabs.smartguard.data.local.room

import androidx.room.*
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryList
import com.hbeonlabs.smartguard.data.local.models.ActivityHistory
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.SecondaryUser
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.room.relations.HubWithSensors
import kotlinx.coroutines.flow.Flow

@Dao
interface HubDao {

    // ================= HUB SQL ============================
    @Query("SELECT * FROM hub")
    fun getAllHubsList(): Flow<List<Hub>>

    @Query("SELECT * FROM hub WHERE hub_serial_number = :hub_id LIMIT 1")
    fun getHubFromId(hub_id: String):Flow<Hub>

    @Query("UPDATE hub SET hub_arm_state = :armDisarmState WHERE hub_serial_number in(:hub_id)")
    suspend fun armDisarmHub(armDisarmState:Boolean,hub_id:String)

    @Query("UPDATE hub SET hub_siren = :sirenRingState WHERE hub_serial_number in(:hub_id)")
    suspend fun sirenRingHub(sirenRingState:Boolean,hub_id:String)

    @Insert
    suspend fun addHub(hub:Hub)

    @Query("SELECT EXISTS(SELECT * FROM hub WHERE hub_serial_number = :hub_serial_no)")
    suspend fun checkIfHubAlreadyPresent(hub_serial_no:String):Boolean

    @Query("SELECT EXISTS(SELECT * FROM hub WHERE hub_phone_number = :hub_phone_no)")
    suspend fun checkIfHubPhoneNumberAlreadyPresent(hub_phone_no:String):Boolean

    @Query("UPDATE hub SET hub_name = :hub_name , hub_image = :hubImage WHERE hub_serial_number in(:hub_serial_no)")
    suspend fun addUpdateHub(hub_name:String ,hubImage:String ,hub_serial_no:String)

    @Query("UPDATE hub SET hub_arm_registered = :boolean WHERE hub_serial_number = :hub_serial_no")
    suspend fun setHubArmRegisteredState(boolean:Boolean,hub_serial_no:String)

    @Query("UPDATE hub SET hub_disarm_registered = :boolean WHERE hub_serial_number = :hub_serial_no")
    suspend fun setHubDisArmRegisteredState(boolean:Boolean,hub_serial_no:String)

    @Query("UPDATE hub SET hub_ring_registered = :boolean WHERE hub_serial_number = :hub_serial_no")
    suspend fun setHubRingRegisteredState(boolean:Boolean,hub_serial_no:String)

    @Query("UPDATE hub SET hub_silence_registered = :boolean WHERE hub_serial_number = :hub_serial_no")
    suspend fun setHubSilenceRegisteredState(boolean:Boolean,hub_serial_no:String)



    // ================ ACTIVITY HISTORY SQL =========================

    // Activity History add
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActivityHistory(activity:ActivityHistory)

    // get activity history by id
    @Query("SELECT * FROM activityhistory WHERE hub_serial_number = :hub_id")
    fun getAllActivities(hub_id: String):Flow<List<ActivityHistory>>


    // ================ SENSORS SQL =====================

    // Get all sensors of a single hub
    @Query("SELECT * FROM sensor WHERE hub_serial_number = :hub_id")
    fun getAllSensorUsingHubId(hub_id: String):Flow<List<Sensor>>

    // delete sensor
    @Delete
    fun deleteSensor(sensor: Sensor):Int

    @Insert
    fun addSensor(sensor: Sensor)

    @Update
    fun editSensor(sensor: Sensor):Int

    // =============== SECONDARY USER SQL ==============
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSecondaryUser(secondaryUser: SecondaryUser)

    @Query("SELECT * FROM secondaryuser WHERE hub_serial_number = :hubId")
    fun getSecondaryUsersUsingHub(hubId: String):Flow<List<SecondaryUser>>


    @Query("UPDATE secondaryuser SET user_name = :name , user_pic = :image , hub_serial_number =:hub_serial_no , user_phone_number =:number WHERE hub_serial_number in(:hub_serial_no)")
    fun updateSecondaryUsers(name:String,image:String,hub_serial_no:String,number:String):Int

    @Delete
    fun deleteSecondaryUser(secondaryUser: SecondaryUser)









}