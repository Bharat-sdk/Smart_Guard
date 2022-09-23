package com.hbeonlabs.smartguard.ui.fragments.sensors
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.repo.SensorRepositoryImp
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.sql.SQLException
import javax.inject.Inject

class SensorViewModel @Inject constructor(
private val repo:SensorRepositoryImp
):BaseViewModel()
{

    private val _mSensorEvents = MutableSharedFlow<ManageSensorEvents>()
    val mSensorEvents: SharedFlow<ManageSensorEvents> = _mSensorEvents

    suspend fun getHubList(hub_serial_no:String):Flow<List<Sensor>> = repo.getAllSensors(hub_serial_no)

    fun deleteSensor(sensor: Sensor)
    {
        viewModelScope.launch {
            try {

                if (repo.deleteSensor(sensor) == 0)
                {
                    _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent("Sensor was not removed pls try again"))
                }else{
                    _mSensorEvents.emit(ManageSensorEvents.DeleteSensorSuccess)
                }

            }
            catch (e:SQLException)
            {
                _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent(e.message.toString()))
            }

        }
    }


    sealed class ManageSensorEvents{
        object DeleteSensorSuccess:ManageSensorEvents()
        class SQLErrorEvent(val message: String):ManageSensorEvents()
    }}