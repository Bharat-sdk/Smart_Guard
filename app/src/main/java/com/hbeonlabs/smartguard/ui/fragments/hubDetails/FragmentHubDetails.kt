package com.hbeonlabs.smartguard.ui.fragments.hubDetails

import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentHubDetailScreenBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.ViewPagerHubFragmentAdapter
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.pagerActivityHistory.FragmentPagerActivityHistory
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm.FragmentPagerSirenArming
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos.FragmentPagerSOS

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

        (requireActivity() as MainActivity).binding.toolbarIconEnd.apply {
            setImageResource(R.drawable.ic_settings)
            visibility = VISIBLE
            setOnClickListener {  }
        }
        (requireActivity() as MainActivity).binding.toolbarIconEnd2.apply {
            setImageResource(R.drawable.ic_baseline_add)
            setOnClickListener { findNavController().navigate(R.id.fragmentAddAHub) }
            visibility = VISIBLE}

        val fragmentList = arrayListOf<Fragment>(
            FragmentPagerSirenArming(),FragmentPagerSOS(),FragmentPagerActivityHistory()
        )

        val adapter = ViewPagerHubFragmentAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)

        binding.hubScreenFragmentViewPager.adapter = adapter

        binding.circularIndicator.setViewPager(binding.hubScreenFragmentViewPager)

        binding.layoutManageSensors.setOnClickListener {
            findNavController().navigate(FragmentHubDetailsDirections.actionFragmentHubDetailsToSecondaryUsersFragment())
        }

    }




}