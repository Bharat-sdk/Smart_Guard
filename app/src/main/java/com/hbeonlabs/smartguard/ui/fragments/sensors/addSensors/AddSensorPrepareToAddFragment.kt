package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddSensorPrepareBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel

import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel


class AddSensorPrepareToAddFragment:BaseFragment<SensorViewModel,FragmentAddSensorPrepareBinding>() {

    private val args:AddSensorFragmentArgs by navArgs()
    private  val addSensorPrepareViewModel by sharedStateViewModel<SensorViewModel>()
    override fun getViewModel(): SensorViewModel {
            return addSensorPrepareViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_sensor_prepare
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            setImageResource(R.drawable.ic_baseline_add)
            visibility = View.INVISIBLE
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE
        }


        binding.getStarted.setOnClickListener {
        findNavController().navigate(AddSensorPrepareToAddFragmentDirections.actionAddSensorPrepareToAddFragmentToSensorTypeListFragment())
        }

    }



}