package com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSirenArmingBinding
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel

import org.koin.android.ext.android.inject


class FragmentPagerSirenArming:BaseFragment<HubDetailsViewModel,FragmentPagerSirenArmingBinding>() {

    private  val pagerSirenArmingViewModel: HubDetailsViewModel by inject()
    override fun getViewModel(): HubDetailsViewModel {
            return pagerSirenArmingViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_siren_arming
    }

    override fun initView() {
        super.initView()

    }



}