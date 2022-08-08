package com.hbeonlabs.smartguard.ui.activities

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hbeonlabs.smartguard.R
import com.hbeonlabs.smartguard.base.BaseActivity
import com.hbeonlabs.smartguard.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
    private lateinit var navController: NavController
    private  val mainViewModel: MainViewModel by inject()
    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        navController = findNavController(R.id.navHostFragment)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        hideShowToolbar()


    }



    fun hideShowToolbar()
    {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitle.text = destination.label

            when (destination.id) {
                R.id.fragmentSplash -> {
                    supportActionBar?.hide()
                }
                R.id.fragmentOnBoarding -> {
                    supportActionBar?.hide()
                }
                else -> {
                    supportActionBar?.show()
                }
            }
        }
    }


}