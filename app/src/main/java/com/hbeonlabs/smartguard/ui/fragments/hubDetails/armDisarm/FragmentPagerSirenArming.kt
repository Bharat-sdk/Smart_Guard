package com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm

import android.content.res.ColorStateList
import android.graphics.PorterDuff.*
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSirenArmingBinding
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.utils.collectLatestLifeCycleFlow
import com.hbeonlabs.smartguard.utils.makeToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

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


        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(100)
            hubDetailsViewModel.getHubFromId(hubDetailsViewModel.hub_id).collect {
                if (it.hub_siren)
                {
                    binding.llHubRing.background = resources.getDrawable(R.drawable.bg_green,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.textRing.setTextColor(resources.getColor(R.color.on_boarding_green,null))
                    }else{
                        binding.textRing.setTextColor(resources.getColor(R.color.on_boarding_green))
                    }
                    binding.imgRing.setColorFilter(ContextCompat.getColor(requireContext(), R.color.on_boarding_green), Mode.MULTIPLY);


                    binding.llHubSilence.background = resources.getDrawable(R.drawable.bg_grey_unselected,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtSilence.setTextColor(resources.getColor(R.color.grey,null))
                    }else{
                        binding.txtSilence.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgSilence.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), Mode.MULTIPLY);

                }
                else{
                    binding.llHubSilence.background = resources.getDrawable(R.drawable.bg_red,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtSilence.setTextColor(resources.getColor(R.color.red,null))
                    }else{
                        binding.txtSilence.setTextColor(resources.getColor(R.color.red))
                    }
                    binding.imgSilence.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), Mode.MULTIPLY);

                    binding.llHubRing.background = resources.getDrawable(R.drawable.bg_grey_unselected,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.textRing.setTextColor(resources.getColor(R.color.grey,null))
                    }else{
                        binding.textRing.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgRing.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), Mode.MULTIPLY);

                }

                if (it.hub_arm_state)
                {
                    binding.llHubArm.background = resources.getDrawable(R.drawable.bg_green,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtArm.setTextColor(resources.getColor(R.color.on_boarding_green,null))
                    }else{
                        binding.txtArm.setTextColor(resources.getColor(R.color.on_boarding_green))
                    }
                    binding.imgArm.setColorFilter(ContextCompat.getColor(requireContext(), R.color.on_boarding_green), Mode.MULTIPLY);

                    binding.llHubDisarm.background = resources.getDrawable(R.drawable.bg_grey_unselected,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.grey,null))
                    }else{
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgDisarm.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), Mode.MULTIPLY);


                }
                else
                {
                    binding.llHubDisarm.background = resources.getDrawable(R.drawable.bg_red,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.red,null))
                    }else{
                        binding.txtDisarm.setTextColor(resources.getColor(R.color.red))
                    }
                    binding.imgDisarm.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), Mode.MULTIPLY);

                    binding.llHubArm.background = resources.getDrawable(R.drawable.bg_grey_unselected,null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.txtArm.setTextColor(resources.getColor(R.color.grey,null))
                    }else{
                        binding.txtArm.setTextColor(resources.getColor(R.color.grey))
                    }
                    binding.imgArm.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), Mode.MULTIPLY);

                }
            }
        }

    }



}