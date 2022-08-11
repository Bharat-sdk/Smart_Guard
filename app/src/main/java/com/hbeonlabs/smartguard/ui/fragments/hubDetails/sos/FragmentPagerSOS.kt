package com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos

import android.view.View
import androidx.fragment.app.Fragment
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentHubDetailScreenBinding
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.ViewPagerHubFragmentAdapter

import org.koin.android.ext.android.inject


class FragmentPagerSOS:BaseFragment<PagerSOSViewModel,FragmentPagerSosBinding>() {

    private  val pagerSOSViewModel: PagerSOSViewModel by inject()
    override fun getViewModel(): PagerSOSViewModel {
            return pagerSOSViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_sos
    }

    override fun initView() {
        super.initView()

    }



}