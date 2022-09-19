package com.hbeonlabs.smartguard.ui.fragments.hubDetails.pagerActivityHistory

import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerActivityHistoryBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel

import org.koin.android.ext.android.inject


class FragmentPagerActivityHistory:BaseFragment<HubDetailsViewModel,FragmentPagerActivityHistoryBinding>() {

    private  val pagerActivityHistoryViewModel: HubDetailsViewModel by inject()
    override fun getViewModel(): HubDetailsViewModel {
            return pagerActivityHistoryViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_activity_history
    }

    override fun initView() {
        super.initView()


        binding.txtSeeFullActivityHistory.setOnClickListener {
                    findNavController().navigate(R.id.fragmentActivityHistory)
        }
    }



}