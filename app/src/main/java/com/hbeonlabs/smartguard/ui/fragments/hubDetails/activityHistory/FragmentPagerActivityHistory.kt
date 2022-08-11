package com.hbeonlabs.smartguard.ui.fragments.hubDetails.activityHistory

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerActivityHistoryBinding

import org.koin.android.ext.android.inject


class FragmentPagerActivityHistory:BaseFragment<PagerActivityHistoryViewModel,FragmentPagerActivityHistoryBinding>() {

    private  val pagerActivityHistoryViewModel: PagerActivityHistoryViewModel by inject()
    override fun getViewModel(): PagerActivityHistoryViewModel {
            return pagerActivityHistoryViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_activity_history
    }

    override fun initView() {
        super.initView()

    }



}