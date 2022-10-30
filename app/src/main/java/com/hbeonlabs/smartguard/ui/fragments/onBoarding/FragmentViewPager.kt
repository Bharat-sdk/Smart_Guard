package com.hbeonlabs.smartguard.ui.fragments.onBoarding

import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.data.local.models.OnBoardingData
import com.hbeonlabs.smartguard.databinding.FragmentOnboardingBinding
import com.hbeonlabs.smartguard.ui.adapters.ViewPagerAdapter
import com.hbeonlabs.smartguard.ui.fragments.splash.SplashviewModel

import org.koin.android.ext.android.inject


class FragmentViewPager:BaseFragment<SplashviewModel,FragmentOnboardingBinding>() {

    private  val onBoardingViewModel: SplashviewModel by inject()
    override fun getViewModel(): SplashviewModel {
            return onBoardingViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_onboarding
    }

    override fun initView() {
        super.initView()

        val fragmentList = arrayListOf<OnBoardingData>(
            OnBoardingData("Welcome to your SmartGuard Hub!","The SmartGuard suite is controlled from the central 'Hub. Be sure to use an active SIM card to communicate with the Hub!", ContextCompat.getDrawable(requireContext(),R.drawable.ic_navigate_back)!!,ContextCompat.getColor(requireContext(),R.color.on_boarding_blue)),
            OnBoardingData("Get Started by adding devices here","Adding a new device to expand your home security is as easy as 3 taps!",ContextCompat.getDrawable(requireContext(),R.drawable.ic_navigate_back)!!,ContextCompat.getColor(requireContext(),R.color.on_boarding_green)),
            OnBoardingData("Add others to your hubs easily","Have the smart guard hub automatically notify other people when any sensor is triggered!", ContextCompat.getDrawable(requireContext(),R.drawable.ic_navigate_back)!!,ContextCompat.getColor(requireContext(),R.color.on_boarding_orange)),
            OnBoardingData("Get help whenever you need it whenever you need it","Help is available at every single step, just tap the 'Help' icon on the top right to get quick answers!", ContextCompat.getDrawable(requireContext(),R.drawable.ic_navigate_back)!!,ContextCompat.getColor(requireContext(),R.color.purple_200)),

            )

        val adapter = ViewPagerAdapter(fragmentList)
        binding.onBoardingViewPager.adapter = adapter

        binding.circularIndicator.setViewPager(binding.onBoardingViewPager)

        binding.onBoardingViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position)
                {
                    0 ->{
                        binding.onBoardingBack.visibility = View.GONE
                        binding.txtNext.text = "Next"
                    }
                    1 ->{
                        binding.onBoardingBack.visibility = View.VISIBLE
                        binding.txtNext.text = "Next"
                    }
                    2 ->{
                        binding.onBoardingBack.visibility = View.VISIBLE
                        binding.txtNext.text ="Next"
                    }
                    3 ->{
                        binding.onBoardingBack.visibility = View.VISIBLE
                        binding.txtNext.text = "Get Started"
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }


        })


        binding.txtNext.setOnClickListener {
            if (  binding.txtNext.text == "Get Started" ){
               findNavController().navigate(R.id.fragmentAddAHub)
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