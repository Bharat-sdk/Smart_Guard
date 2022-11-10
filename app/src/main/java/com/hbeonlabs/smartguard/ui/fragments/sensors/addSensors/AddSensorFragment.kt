package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.databinding.FragmentAddASensorBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.hideKeyboard
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import java.util.*


class AddSensorFragment:BaseFragment<SensorViewModel,FragmentAddASensorBinding>() {

    val args : AddSensorFragmentArgs by navArgs()

    private  val addSensorViewModel  by sharedStateViewModel<SensorViewModel>()
    override fun getViewModel(): SensorViewModel {
            return addSensorViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_a_sensor
    }

    override fun initView() {
        super.initView()
        observe()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            setImageResource(R.drawable.ic_baseline_add)
            visibility = View.INVISIBLE
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE
        }

        binding.clAddASensor.setOnClickListener {
            requireContext().hideKeyboard(it)
        }

        binding.btnAddSensor.setOnClickListener {
            val sensorName = binding.edtAddSensorName.text.toString()
            val customSmsMessage = binding.edtAddSensorCustomSmsMessage.text.toString()
            val curTimeStamp = Calendar.getInstance().timeInMillis
            val sensor = Sensor(null,sensorName,"",args.sensorType.sensor_model_number,false,customSmsMessage,curTimeStamp.toString(),addSensorViewModel.hub_serial_no,"")
            lifecycleScope.launch{
              val size =   addSensorViewModel.getSensorsListSize(sensor.hub_serial_number)
                if (size <0 || size>=8)
                {
                    // todo toast
                }

                else{
                    // todo send sms to add sensor
                }
            }
            addSensorViewModel.addSensor(sensor)
        }

    }


    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            addSensorViewModel.mSensorEvents.collectLatest {
                when (it) {
                    SensorViewModel.ManageSensorEvents.AddSensorSuccess -> {
                        findNavController().navigate(AddSensorFragmentDirections.actionAddSensorFragmentToSensorListFragment(addSensorViewModel.hub_serial_no))
                    }
                    is SensorViewModel.ManageSensorEvents.SQLErrorEvent -> {
                        makeToast(it.message)
                    }
                    else -> {}
                }
            }
        }



    }



}