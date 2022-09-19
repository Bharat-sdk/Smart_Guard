package com.hbeonlabs.smartguard.ui.fragments.sensors

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentSensorListBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SensorListAdapter

import org.koin.android.ext.android.inject


class SensorListFragment:BaseFragment<SensorViewModel,FragmentSensorListBinding>() {

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
            setImageResource(R.drawable.ic_baseline_add)
            visibility = View.VISIBLE
            setOnClickListener {
                // go to add sensor
                findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToAddSensorPrepareToAddFragment())
            }
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
        }


        val adapter = SensorListAdapter(requireContext())
        binding.adapter = adapter
        adapter.setDeleteUserClickListener { sensor, i ->

        }

        adapter.setDeleteUserClickListener { sensor, i ->

        }

    }



}