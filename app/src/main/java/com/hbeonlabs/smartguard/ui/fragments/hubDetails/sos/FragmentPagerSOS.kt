package com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import org.koin.android.ext.android.inject

@SuppressLint("ClickableViewAccessibility")
class FragmentPagerSOS:BaseFragment<HubDetailsViewModel,FragmentPagerSosBinding>() {

    private  val pagerSOSViewModel: HubDetailsViewModel by inject()
    override fun getViewModel(): HubDetailsViewModel {
            return pagerSOSViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_pager_sos
    }

    override fun initView() {
        super.initView()

        binding.btnSos.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN ->{
                    getViewModel().startPress()
                }
                MotionEvent.ACTION_UP ->{
                    getViewModel().resetPress()
                }
            }
            true
        }


        lifecycleScope.launchWhenStarted {
            getViewModel().progressIndicatorLiveData.collect{
                binding.progress2Sec.progress = it
                if (it ==100)
                {
                    snackBar("Done")
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        getViewModel().resetPress()
    }

}