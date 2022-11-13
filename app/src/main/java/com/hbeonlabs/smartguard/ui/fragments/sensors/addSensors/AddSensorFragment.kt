package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.app.Dialog
import android.content.IntentFilter
import android.provider.Telephony
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.databinding.FragmentAddASensorBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.dialogs.dialogVerifySensorAddition
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import java.util.*


class AddSensorFragment: BaseFragment<SensorViewModel, FragmentAddASensorBinding>(),
    SmsBroadcastReceiver.Listener {
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    lateinit var dialog:Dialog
    lateinit var sensor: Sensor
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
      // =========  Setting Sms Listener ==================
        smsBroadcastReceiver = SmsBroadcastReceiver()
        requireActivity().registerReceiver(
            smsBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
        smsBroadcastReceiver.setListener(this)
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

            addSensorViewModel.getSensorsListSize(addSensorViewModel.hub_serial_no)
        }

    }


    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            addSensorViewModel.mSensorEvents.collectLatest {
                when (it) {
                    SensorViewModel.ManageSensorEvents.AddSensorSuccess -> {
                        findNavController().navigate(AddSensorFragmentDirections.actionAddSensorFragmentToSensorListFragment(addSensorViewModel.hub_serial_no,addSensorViewModel.hub!!))
                    }
                    is SensorViewModel.ManageSensorEvents.SQLErrorEvent -> {
                        makeToast(it.message)
                    }
                    else -> {}
                }
            }
        }

        collectLatestLifeCycleFlow(addSensorViewModel.listSizeEvent){ size->

            val sensorName = binding.edtAddSensorName.text.toString()
            val customSmsMessage = binding.edtAddSensorCustomSmsMessage.text.toString()
            val curTimeStamp = Calendar.getInstance().timeInMillis
            sensor = Sensor(null,sensorName,"",args.sensorType.sensor_model_number,false,customSmsMessage,curTimeStamp.toString(),addSensorViewModel.hub_serial_no,"")
            // ======== Checking the no. of sensors already added to hub ===========
            if (size <0 || size>=8)
            {
                makeToast("Maximum 8 Hubs are added")
            }
            else{
                if (sensorName.isNotBlank() || customSmsMessage.isNotBlank())
                {
                    binding.loading.visibility = View.VISIBLE
                    addSensorViewModel.hub?.let { hub -> sendSMS(hub.hub_phone_number,"${hub.hub_serial_number} S0${size+1} $customSmsMessage #"){} }
                }
                else{
                    makeToast("Please fill all the fields to continue")
                }
            }
        }


    }


    override fun onTextReceived(text: String?, smsSender: String?) {
        binding.loading.visibility = View.INVISIBLE
        if (text!=null)
        {
            if (text.startsWith("Activate Sensor to save Sensor"))
            {
                dialog = dialogVerifySensorAddition()

            }
            else if (text.startsWith("Sensor ID : "))
            {
                dialog.dismiss()
                addSensorViewModel.addSensor(sensor)
            }
            else if (text == "Configuration timeout")
            {
                dialog.dismiss()
            }

        }
    }


}