package com.hbeonlabs.smartguard.ui.fragments.hubSettings

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.databinding.FragmentHubSettingsBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity

import org.koin.android.ext.android.inject


class FragmentHubSettings:BaseFragment<HubSettingsViewModel,FragmentHubSettingsBinding>() {

    private  val hubSettingsViewModel: HubSettingsViewModel by inject()
    override fun getViewModel(): HubSettingsViewModel {
            return hubSettingsViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_hub_settings
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_check_24)
           // Edit Hub Functionality
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.INVISIBLE

        binding.descManageSecondaryNum.setOnClickListener {
            findNavController().navigate(R.id.secondaryUsersFragment)
        }




    }



}