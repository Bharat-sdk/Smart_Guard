package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentSensorTypeListBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SensorTypesListAdapter
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject


class SensorTypeListFragment:BaseFragment<SensorViewModel,FragmentSensorTypeListBinding>() {
    val args :SensorTypeListFragmentArgs by navArgs()

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
        val appLists = AppLists(requireContext())

        adapter.differ.submitList(appLists.sensorTypeLists)
        adapter.setClickListener { sensorTypes, i ->
            findNavController().navigate(SensorTypeListFragmentDirections.actionSensorTypeListFragmentToAddSensorFragment(args.hubSerialNo,sensorTypes))
        }


        }

    }
