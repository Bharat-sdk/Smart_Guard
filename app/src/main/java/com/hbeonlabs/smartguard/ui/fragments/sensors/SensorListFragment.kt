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
import com.hbeonlabs.smartguard.utils.AppConstants
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest

import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel


class SensorListFragment:BaseFragment<SensorViewModel,FragmentSensorListBinding>() {
    private val args:SensorListFragmentArgs by navArgs()
    lateinit var adapter: SensorListAdapter

    private  val sensorListViewModel by sharedStateViewModel<SensorViewModel>()
    override fun getViewModel(): SensorViewModel {
            return sensorListViewModel
    }

    override fun getLayoutResourceId()=R.layout.fragment_sensor_list


    override fun initView() {
        super.initView()
        sensorListViewModel.hub_serial_no =args.hubSerialNo
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
        adapter.setDeleteSensorClickListener { sensor, _ ->
            getViewModel().deleteSensor(sensor)
        }

        adapter.setEditSensorClickListener{ sensor, _ ->

            findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToEditSensorFragment(sensor))

        }

        adapter.setSensorClickListener { sensor, _ ->
            findNavController().navigate(SensorListFragmentDirections.actionSensorListFragmentToSensorDetailsFragment(sensor))
        }

    }

    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getViewModel().getHubList(args.hubSerialNo).collectLatest {
                adapter.differ.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            getViewModel().mSensorEvents.collectLatest {
                when(it)
                {
                    SensorViewModel.ManageSensorEvents.DeleteSensorSuccess -> {
                        makeToast(AppConstants.DELETE_SENSOR_SUCCESSFULLY)
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