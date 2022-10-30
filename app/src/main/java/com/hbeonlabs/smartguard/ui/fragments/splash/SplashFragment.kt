package com.hbeonlabs.smartguard.ui.fragments.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentSplashBinding
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject


class SplashFragment:BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    private  val splashViewModel: SplashViewModel by inject()

    override fun getViewModel(): SplashViewModel {
            return splashViewModel
    }

    override fun getLayoutResourceId()= R.layout.fragment_splash


    override fun initView() {
        super.initView()
        splashViewModel.splashToHome()
        observe()


    }

    private fun observe()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            splashViewModel.splashEvent.collectLatest {
                when(it)
                {
                    SplashEvent.NavigateToHubEvent -> {
                        findNavController().navigate(SplashFragmentDirections.actionFragmentSplashToFragmentSelectAHub())
                    }
                    SplashEvent.NavigateToOnBoardingEvent -> {
                        findNavController().apply {
                            navigate(SplashFragmentDirections.actionFragmentSplashToFragmentOnBoarding())
                        }
                    }
                }
            }
        }

    }



}