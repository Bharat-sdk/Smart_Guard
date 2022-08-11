package com.hbeonlabs.smartguard.ui.fragments.hubDetails

import android.view.View
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentHubDetailScreenBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity

import org.koin.android.ext.android.inject


class FragmentHubDetails:BaseFragment<HubDetailsViewModel,FragmentHubDetailScreenBinding>() {

    private  val hubDetailsViewModel: HubDetailsViewModel by inject()
    override fun getViewModel(): HubDetailsViewModel {
            return hubDetailsViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_hub_detail_screen
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.setImageResource(R.drawable.ic_baseline_help)
        (requireActivity() as MainActivity).binding.toolbarIconStart.visibility = View.GONE


    }



}