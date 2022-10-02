package com.hbeonlabs.smartguard.di

import android.app.Application
import androidx.room.Room
import com.hbeonlabs.smartguard.data.local.datastore.SharedPreferences
import com.hbeonlabs.smartguard.data.local.repo.HubRepositoryImp
import com.hbeonlabs.smartguard.data.local.repo.SensorRepositoryImp
import com.hbeonlabs.smartguard.data.local.room.AppDatabase
import com.hbeonlabs.smartguard.data.local.room.HubDao
import com.hbeonlabs.smartguard.ui.activities.MainViewModel
import com.hbeonlabs.smartguard.ui.fragments.activityHistory.ActivityHistoryViewModel
import com.hbeonlabs.smartguard.ui.fragments.addAHub.AddAHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubDetails.HubDetailsViewModel
import com.hbeonlabs.smartguard.ui.fragments.hubSettings.HubSettingsViewModel
import com.hbeonlabs.smartguard.ui.fragments.postAddHub.PostAddHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.secondoryUser.SecondaryUserViewModel
import com.hbeonlabs.smartguard.ui.fragments.selectHub.SelectHubViewModel
import com.hbeonlabs.smartguard.ui.fragments.sensors.SensorViewModel
import com.hbeonlabs.smartguard.ui.fragments.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { AddAHubViewModel(get()) }
    viewModel { SelectHubViewModel(get()) }
    viewModel { PostAddHubViewModel(get()) }
    viewModel { HubDetailsViewModel(get()) }
    viewModel { ActivityHistoryViewModel(get()) }
    viewModel { SecondaryUserViewModel() }
    viewModel { HubSettingsViewModel(get()) }
    viewModel { SensorViewModel(get()) }

    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "smart_guard_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AppDatabase): HubDao {
        return dataBase.getDao()
    }


    single {
       provideDataBase(androidApplication())
    }

    single {
        provideDao(get())
    }

    single {

        HubRepositoryImp(get(),get())
    }


    single {
        SensorRepositoryImp(get())
    }

    single {
        SharedPreferences(androidApplication())
    }


}