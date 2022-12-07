package com.hbeonlabs.smartguard.ui.fragments.sensors

import android.content.IntentFilter
import android.provider.Telephony
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.Hub
import com.hbeonlabs.smartguard.data.local.models.Sensor
import com.hbeonlabs.smartguard.databinding.FragmentSensorListBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SensorListAdapter
import com.hbeonlabs.smartguard.utils.SmsBroadcastReceiver
import com.hbeonlabs.smartguard.utils.makeToast
import com.hbeonlabs.smartguard.utils.sendSMS
import kotlinx.coroutines.flow.collectLatest

import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel


class SensorListFragment:BaseFragment<SensorViewModel,FragmentSensorListBinding>(),
    SmsBroadcastReceiver.Listener {
    private val args:SensorListFragmentArgs by navArgs()
    lateinit var adapter: SensorListAdapter
    lateinit var hub:Hub
    lateinit var sensor :Sensor

    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver


    private  val sensorListViewModel by sharedStateViewModel<SensorViewModel>()
    override fun getViewModel(): SensorViewModel {
            return sensorListViewModel
    }



    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_sensor_list
    }

    override fun initView() {
        super.initView()
        hub = args.hub

        // =========  Setting Sms Listener ==================
        smsBroadcastReceiver = SmsBroadcastReceiver()
        requireActivity().registerReceiver(
            smsBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
        smsBroadcastReceiver.setListener(this)

        sensorListViewModel.hub_serial_no =args.hubSerialNo
        sensorListViewModel.hub = args.hub
        observe()
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            setImageResource(R.drawable.ic_baseline_add)
            visibility = View.VISIBLE
            setOnClickListener {
                // go to add sensor
                findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToAddSensorPrepareToAddFragment())
            }
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.apply {
            visibility = View.INVISIBLE
        }


        adapter = SensorListAdapter(requireContext())
        binding.adapter = adapter
        adapter.setDeleteSensorClickListener { sensor, i ->
            //====================== Deleting Sensor Code Is DO1 to D08 ===============
            sendSMS(hub.hub_phone_number,"${hub.hub_serial_number} D0${sensor.sensor_slot}"){}
        }

        adapter.setEditSensorClickListener{ sensor, i ->

            findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToEditSensorFragment(sensor))

        }

        adapter.setSensorClickListener { sensor, position ->
            findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToSensorDetailsFragment(sensor))
        }

    }

    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getViewModel().getSensorsList(args.hubSerialNo).collectLatest {
                adapter.differ.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getViewModel().mSensorEvents.collectLatest {
                when(it)
                {
                    SensorViewModel.ManageSensorEvents.DeleteSensorSuccess -> {
                        makeToast("Sensor Removed Successfully")
                    }
                    is SensorViewModel.ManageSensorEvents.SQLErrorEvent -> {
                        makeToast(it.message)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onTextReceived(text: String?, smsSender: String?) {
        //

        getViewModel().deleteSensor(sensor)

    }


}