package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.view.View
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.*
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel

import org.koin.android.ext.android.inject


class SensorDetailsFragment:BaseFragment<SensorViewModel,FragmentSensorDetailsBinding>() {

    private  val activityHistoryViewModel: SensorViewModel by inject()
    override fun getViewModel(): SensorViewModel {
            return activityHistoryViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_sensor_details
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
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

            }
        }



    }



}