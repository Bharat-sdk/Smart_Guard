package com.hbeonlabs.smartguard.ui.fragments.addAHub

import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.OnBoardingData
import com.hbeonlabs.smartguard.databinding.FragmentAddAHubBinding
import com.hbeonlabs.smartguard.databinding.FragmentOnboardingBinding
import com.hbeonlabs.smartguard.ui.activities.MainActivity
import com.hbeonlabs.smartguard.ui.adapters.ViewPagerAdapter
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.splash.SplashviewModel

import org.koin.android.ext.android.inject


class FragmentAddAHub:BaseFragment<AddAHubViewModel,FragmentAddAHubBinding>() {

    private  val addAHubViewModel: AddAHubViewModel by inject()
    override fun getViewModel(): AddAHubViewModel {
            return addAHubViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_a_hub
    }

    override fun initView() {
        super.initView()

        (requireActivity() as MainActivity).binding.toolbarIconEnd.setImageResource(R.drawable.ic_baseline_help)
        (requireActivity() as MainActivity).binding.toolbarIconStart.visibility = View.GONE
    }



}