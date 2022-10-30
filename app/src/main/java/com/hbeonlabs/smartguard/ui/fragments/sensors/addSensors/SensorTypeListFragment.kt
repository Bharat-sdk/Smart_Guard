package com.hbeonlabs.smartguard.ui.fragments.sensors.addSensors

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.SensorTypes
import com.hbeonlabs.smartguard.databinding.FragmentSensorTypeListBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.SensorTypesListAdapter
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.utils.AppLists
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SensorTypeListFragment:BaseFragment<SensorViewModel,FragmentSensorTypeListBinding>() {

    private  val sensorViewModel by sharedStateViewModel<SensorViewModel>()
    override fun getViewModel(): SensorViewModel {
            return sensorViewModel
    }

    override fun getLayoutResourceId()= R.layout.fragment_sensor_type_list


    override fun initView() {
        super.initView()
        observe()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE
        }

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.INVISIBLE
        }


        binding.rvSensorTypeList.layoutManager = GridLayoutManager(requireContext(),2)
        val adapter = SensorTypesListAdapter(requireContext())
        binding.adapter = adapter
        val appLists = AppLists(requireContext())

        adapter.differ.submitList(appLists.sensorTypeLists)
        adapter.setClickListener { sensorTypes, _ ->
           sensorViewModel.navigateToSensorFragment(sensorTypes)
        }

        }


    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sensorViewModel.mSensorEvents.collectLatest {
                when (it) {
                    SensorViewModel.ManageSensorEvents.DeleteSensorSuccess -> {

                    }
                    is SensorViewModel.ManageSensorEvents.NavigateToAddSensorFragment -> {
                        findNavController().navigate(SensorTypeListFragmentDirections.actionSensorTypeListFragmentToAddSensorFragment(it.sensorTypes))
                    }
                    is SensorViewModel.ManageSensorEvents.SQLErrorEvent -> makeToast(it.message)
                    else -> {}
                }
            }
        }
    }

    }


