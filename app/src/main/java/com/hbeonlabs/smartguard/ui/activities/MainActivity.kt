package com.hbeonlabs.smartguard.ui.activities

import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseActivity
import com.hbeonlabs.smartguard.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {

    private  val mainViewModel: MainViewModel by inject()
    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()

    }


}