package com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSirenArmingBinding
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding

import org.koin.android.ext.android.inject


class FragmentPagerSirenArming:BaseFragment<PagerSirenArmingViewModel,FragmentPagerSirenArmingBinding>() {

    private  val pagerSirenArmingViewModel: PagerSirenArmingViewModel by inject()
    override fun getViewModel(): PagerSirenArmingViewModel {
            return pagerSirenArmingViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_siren_arming
    }

    override fun initView() {
        super.initView()

    }



}