package com.hbeonlabs.smartguard.ui.fragments.sensors
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hbeonlabs.smartguard.base.BaseViewModel
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.data.local.models.SensorTypes
import com.hbeonlabs.smartguard.data.local.repo.SensorRepositoryImp
import kotlinx.coroutines.Dispatchers
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

    private val _listSizeEvent = MutableSharedFlow<Int>()
    val listSizeEvent: SharedFlow<Int> = _listSizeEvent

    var hub_serial_no = ""
    var hub:Hub? = null



    suspend fun getSensorsList(hub_serial_no:String):Flow<List<Sensor>> = repo.getAllSensors(hub_serial_no)

     private suspend fun getSensorsArray(hub_serial_no:String):Array<Sensor> = repo.getAllSensorsList(hub_serial_no)

     fun getSensorsListSize(hub_serial_no:String) {
         viewModelScope.launch (Dispatchers.IO){
             val data =  repo.getAllSensorsList(hub_serial_no)
             _listSizeEvent.emit(data.size)
         }

    }


    fun deleteSensor(sensor: Sensor)
    {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun addSensor(sensor: Sensor){
        viewModelScope.launch(Dispatchers.IO){
            if (sensor.sensor_name.isBlank() || sensor.sensor_custom_sms.isBlank())
            {
                _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent("Please Fill All The Fields"))
            }
            else{
                val size = getSensorsArray(sensor.hub_serial_number).size

                if (size<0)
                {
                    _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent("Having an error please try again"))
                }
                else if (size>=8)
                {
                    _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent("8 Sensors are already added cant add more sensors."))
                }
                else{
                    try {
                        sensor.sensor_slot = "S0${size+1}"
                        repo.addSensor(sensor)
                        _mSensorEvents.emit(ManageSensorEvents.AddSensorSuccess)
                    }
                    catch (e:SQLException)
                    {
                        _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent(e.message.toString()))
                    }
                }



            }


        }
    }

    fun editSensor(sensor: Sensor){
        viewModelScope.launch(Dispatchers.IO){
            if (sensor.sensor_name.isBlank() || sensor.sensor_custom_sms.isBlank())
            {
                _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent("Please Fill All The Fields"))
            }
            else{
                try {
                    repo.editSensor(sensor)
                    _mSensorEvents.emit(ManageSensorEvents.EditSensorSuccess)
                }
                catch (e:SQLException)
                {
                    _mSensorEvents.emit(ManageSensorEvents.SQLErrorEvent(e.message.toString()))
                }
            }


        }
    }



    fun navigateToSensorFragment(sensorTypes: SensorTypes)
    {
        viewModelScope.launch {
            _mSensorEvents.emit(ManageSensorEvents.NavigateToAddSensorFragment(sensorTypes))
        }
    }




    sealed class ManageSensorEvents{
        object DeleteSensorSuccess:ManageSensorEvents()
        object AddSensorSuccess:ManageSensorEvents()
        object EditSensorSuccess:ManageSensorEvents()
        class SQLErrorEvent(val message: String):ManageSensorEvents()
        class NavigateToAddSensorFragment(val sensorTypes: SensorTypes):ManageSensorEvents()
    }

}