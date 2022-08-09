package com.hbeonlabs.smartguard.di

import com.hbeonlabs.smartguard.ui.activities.MainViewModel
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.selectHub.SelectHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.splash.SplashviewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashviewModel() }

    viewModel { MainViewModel() }

    viewModel { AddAHubViewModel() }

    viewModel { SelectHubViewModel() }
}