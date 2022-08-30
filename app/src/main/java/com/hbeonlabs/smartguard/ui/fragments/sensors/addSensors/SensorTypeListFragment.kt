package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryDate
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistoryItem
import com.hbeonlabs.smartguard.data.local.activityModels.ActivityHistory
import com.hbeonlabs.smartguard.databinding.FragmentActivityHistoryBinding
import com.hbeonlabs.smartguard.databinding.FragmentAddASensorBinding
import com.hbeonlabs.smartguard.databinding.FragmentSensorListBinding
import com.hbeonlabs.smartguard.databinding.FragmentSensorTypeListBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.ActivityHistoryAdapter
import com.hbeonlabs.smartguard.ui.adapters.SensorListAdapter
import com.hbeonlabs.smartguard.ui.adapters.SensorTypesListAdapter
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.FragmentHubHomeDirections
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject
import org.koin.androidx.scope.fragmentScope
import java.text.SimpleDateFormat
import java.util.*


class SensorTypeListFragment:BaseFragment<SensorViewModel,FragmentSensorTypeListBinding>() {

    private  val activityHistoryViewModel: SensorViewModel by inject()
    override fun getViewModel(): SensorViewModel {
            return activityHistoryViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_activity_history
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE
        }


        val adapter = SensorTypesListAdapter(requireContext())
        binding.adapter = adapter
        adapter.setClickListener { sensorTypes, i ->

        }


        }

    }
