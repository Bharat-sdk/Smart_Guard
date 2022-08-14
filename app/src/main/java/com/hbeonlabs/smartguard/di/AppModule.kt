package com.hbeonlabs.smartguard.di

import com.hbeonlabs.smartguard.ui.activities.MainViewModel
import com.hbeonlabs.smartguard.ui.fragments.activityHistory.ActivityHistoryViewModel
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.pagerActivityHistory.PagerActivityHistoryViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.armDisarm.PagerSirenArmingViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.sos.PagerSOSViewModel
import com.hbeonlabs.smartguard.ui.fragments.postAddHub.PostAddHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.selectHub.SelectHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.splash.SplashviewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashviewModel() }
    viewModel { MainViewModel() }
    viewModel { AddAHubViewModel() }
    viewModel { SelectHubViewModel() }
    viewModel { PostAddHubViewModel() }
    viewModel { HubDetailsViewModel() }
    viewModel { PagerSOSViewModel() }
    viewModel { PagerActivityHistoryViewModel() }
    viewModel { PagerSirenArmingViewModel() }
    viewModel { ActivityHistoryViewModel() }


}