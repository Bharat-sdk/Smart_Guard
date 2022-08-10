package com.hbeonlabs.smartguard.ui.fragments.editHub

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentEditHubAddSensorsBinding

import org.koin.android.ext.android.inject


class FragmentEditHub:BaseFragment<EditHubViewModel,FragmentEditHubAddSensorsBinding>() {

    private  val editHubViewModel: EditHubViewModel by inject()
    override fun getViewModel(): EditHubViewModel {
            return editHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_edit_hub_add_sensors
    }

    override fun initView() {
        super.initView()

/*
        (requireActivity() as MainActivity).binding.toolbarIconEnd.setImageResource(R.drawable.ic_baseline_help)
        (requireActivity() as MainActivity).binding.toolbarIconStart.visibility = View.GONE
*/


    }



}