package com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseFragment
import com.hbeonlabs.smartguard.databinding.FragmentPagerSosBinding
import org.koin.android.ext.android.inject

@SuppressLint("ClickableViewAccessibility")
class FragmentPagerSOS:BaseFragment<PagerSOSViewModel,FragmentPagerSosBinding>() {

    private  val pagerSOSViewModel: PagerSOSViewModel by inject()
    override fun getViewModel(): PagerSOSViewModel {
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