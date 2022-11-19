package com.hbeonlabs.smartguard.ui.activities

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
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

        val navHostFrag = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFrag.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)

        setupActionBarWithNavController(navController)
        hideShowToolbar()

        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination.id == R.id.fragmentSelectAHub) {
                binding.toolbar.navigationIcon = null
            } else {
            }
        }
    }



    private fun hideShowToolbar()
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}