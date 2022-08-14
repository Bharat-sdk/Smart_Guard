package com.hbeonlabs.smartguard.ui.fragments.hubDetails

import android.view.View
import androidx.fragment.app.Fragment
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

        (requireActivity() as MainActivity).binding.toolbarIconEnd2.setImageResource(R.drawable.ic_baseline_help)
        (requireActivity() as MainActivity).binding.toolbarIconEnd.visibility = View.VISIBLE
        (requireActivity() as MainActivity).binding.toolbarIconEnd.setImageResource(R.drawable.ic_baseline_add)
        (requireActivity() as MainActivity).binding.toolbarIconStart.visibility = View.GONE

        val fragmentList = arrayListOf<Fragment>(
            FragmentPagerSirenArming(),FragmentPagerSOS(),FragmentPagerActivityHistory()
        )

        val adapter = ViewPagerHubFragmentAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        binding.hubScreenFragmentViewPager.adapter = adapter

        binding.circularIndicator.setViewPager(binding.hubScreenFragmentViewPager)


    }



}