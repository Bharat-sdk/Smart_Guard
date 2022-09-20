package com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSirenArmingBinding
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.makeToast

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel


class FragmentPagerSirenArming:BaseFragment<HubDetailsViewModel,FragmentPagerSirenArmingBinding>() {

    private  val hubDetailsViewModel by sharedStateViewModel<HubDetailsViewModel>()
    override fun getViewModel(): HubDetailsViewModel {
            return hubDetailsViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_siren_arming
    }

    override fun initView() {
        super.initView()
        observe()

        binding.cardHubDisarm.setOnClickListener {
            hubDetailsViewModel.armDisarmHub(false)
        }
        binding.cardHubArm.setOnClickListener {
            hubDetailsViewModel.armDisarmHub(true)
        }

        binding.cardHubRing.setOnClickListener {
            hubDetailsViewModel.silenceRingHub(true)
        }

        binding.cardHubSilence.setOnClickListener {
            hubDetailsViewModel.silenceRingHub(false)
        }

    }

    private fun observe()
    {
        collectLatestLifeCycleFlow(hubDetailsViewModel.hubEvents)
        {
            when(it)
            {
                is HubDetailsViewModel.HubDetailsEvents.ArmDisarmEvent -> {
                    makeToast(it.message)
                }
                is HubDetailsViewModel.HubDetailsEvents.SQLErrorEvent -> {
                    makeToast(it.message)
                }
                is HubDetailsViewModel.HubDetailsEvents.SilenceRingEvent -> {
                    makeToast(it.message)
                }
            }
        }
    }



}