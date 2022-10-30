package com.hbeonlabs.smartguard.ui.fragments.onBoarding

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentOnboardingBinding
import com.hbeonlabs.smartguard.ui.adapters.ViewPagerAdapter
import com.hbeonlabs.smartguard.ui.fragments.splash.SplashViewModel
import com.hbeonlabs.smartguard.utils.AppLists

import org.koin.android.ext.android.inject


class FragmentOnBoarding:BaseFragment<SplashViewModel,FragmentOnboardingBinding>() {

    private  val onBoardingViewModel: SplashViewModel by inject()
    override fun getViewModel(): SplashViewModel {
            return onBoardingViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_onboarding
    }

    override fun initView() {
        super.initView()

        val fragmentList = AppLists(requireContext()).fragmentList

        val adapter = ViewPagerAdapter(fragmentList)
        binding.onBoardingViewPager.adapter = adapter

        binding.circularIndicator.setViewPager(binding.onBoardingViewPager)

        binding.onBoardingViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                var visibility = View.GONE
                var text = getString(R.string.next)
                when(position)
                {
                    0 ->{
                        visibility = View.GONE
                        text = getString(R.string.next)
                    }
                    1,2 ->{
                        visibility = View.VISIBLE
                        text = getString(R.string.next)
                    }
                    3 ->{
                        visibility= View.VISIBLE
                        text = getString(R.string.get_started)
                    }
                }
                binding.onBoardingBack.visibility = visibility
                binding.txtNext.text = text
            }
        })

        binding.txtNext.setOnClickListener {
            if (  binding.txtNext.text == getString(R.string.get_started) ){
                onBoardingViewModel.changeIsFirstLoggedIn()
               findNavController().navigate(FragmentOnBoardingDirections.actionFragmentOnBoardingToFragmentSelectAHub())
            }
            else{
                binding.onBoardingViewPager.setCurrentItem(binding.onBoardingViewPager.currentItem+1,true )
            }
        }

        binding.txtBack.setOnClickListener {
            binding.onBoardingViewPager.setCurrentItem(binding.onBoardingViewPager.currentItem-1,true )
        }

    }



}