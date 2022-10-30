package com.hbeonlabs.smartguard.ui.fragments.splash

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.collectLatest

import org.koin.android.ext.android.inject


class SplashFragment:BaseFragment<SplashviewModel, FragmentSplashBinding>() {

    private  val splashViewModel by viewModels<SplashviewModel>()
    override fun getViewModel(): SplashviewModel {
            return splashViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_splash
    }

    override fun initView() {
        super.initView()
        splashViewModel.splashToHome()
        observe()


    }

    fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            splashViewModel.splashEvent.collectLatest {
                when(it)
                {
                    SplashEvent.NavigateToHubEvent -> {

                    }
                    SplashEvent.NavigateToOnBoardingEvent -> {
                        findNavController().navigate(R.id.fragmentOnBoarding)
                        Log.d("TAG", "observe: ")
                    }
                }
            }
        }

    }



}