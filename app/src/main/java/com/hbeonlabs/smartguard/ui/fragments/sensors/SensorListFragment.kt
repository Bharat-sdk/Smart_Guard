package com.hbeonlabs.smartguard.ui.fragments.sensors

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentSensorListBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SensorListAdapter
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest

import org.koin.android.ext.android.inject


class SensorListFragment:BaseFragment<SensorViewModel,FragmentSensorListBinding>() {
    private val args:SensorListFragmentArgs by navArgs()
    lateinit var adapter: SensorListAdapter

    private  val activityHistoryViewModel: SensorViewModel by inject()
    override fun getViewModel(): SensorViewModel {
            return activityHistoryViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_activity_history
    }

    override fun initView() {
        super.initView()

        observe()
        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            setImageResource(R.drawable.ic_baseline_add)
            visibility = View.VISIBLE
            setOnClickListener {
                // go to add sensor
                findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToAddSensorPrepareToAddFragment(args.hubSerialNumber))
            }
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.apply {
            visibility = View.INVISIBLE
        }


        adapter = SensorListAdapter(requireContext())
        binding.adapter = adapter
        adapter.setDeleteUserClickListener { sensor, i ->
            getViewModel().deleteSensor(sensor)
        }

        adapter.setEditUserClickListener{ sensor, i ->

        }

    }

    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getViewModel().getHubList(args.hubSerialNumber).collectLatest {
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
                }
            }
        }
    }



}