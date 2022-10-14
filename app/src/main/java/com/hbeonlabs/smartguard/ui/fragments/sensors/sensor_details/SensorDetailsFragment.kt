package com.hbeonlabs.smartguard.ui.fragments.sensors.sensor_details

import android.view.View
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddASensorBinding
import com.hbeonlabs.smartguard.databinding.FragmentSensorDetailsBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.makeToast

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import java.text.SimpleDateFormat


class SensorDetailsFragment: BaseFragment<SensorViewModel, FragmentSensorDetailsBinding>() {

    val args:SensorDetailsFragmentArgs by navArgs()

    private  val sensorDetailViewModel by sharedStateViewModel<SensorViewModel>()
    override fun getViewModel(): SensorViewModel {
            return sensorDetailViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_sensor_details
    }

    override fun initView() {
        super.initView()
        observe()

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.apply {
            setImageResource(R.drawable.ic_baseline_edit_24)
            visibility = View.VISIBLE
            setOnClickListener {
                // navigate to edit sensor 
            }
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            setImageResource(R.drawable.ic_baseline_delete_24)
            visibility = View.VISIBLE
            setOnClickListener {
                // remove sensor
                sensorDetailViewModel.deleteSensor(args.sensor)
            }
        }

        binding.sensorData = args.sensor
        (requireActivity() as MainActivity).binding.toolbarTitle.text = args.sensor.sensor_name
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val registeredOn = formatter.format(args.sensor.sensor_registered_on.toLong())

        binding.sensorRegisteredOn.text = registeredOn
        if (args.sensor.sensor_image.isEmpty()) {
            binding.ivDetailHubImage.setImageResource(R.drawable.default_sensor_image)

        }
        else{
            binding.ivDetailHubImage.setImageURI(args.sensor.sensor_image.toUri())
        }





    }

    fun observe()
    {
        collectLatestLifeCycleFlow(sensorDetailViewModel.mSensorEvents)
        {
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