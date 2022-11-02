package com.hbeonlabs.smartguard.app

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.provider.Telephony
import androidx.multidex.MultiDex
import com.hbeonlabs.smartguard.di.appModule
import com.hbeonlabs.smartguard.utils.SmsBroadcastReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class SmartGuard : Application() {
    //lateinit var smsBroadcastReceiver:SmsBroadcastReceiver

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        startKoin {
            androidLogger()
            androidContext(this@SmartGuard)
            modules(appModule)
        }
    }

    override fun onCreate() {
        super.onCreate()
        /*smsBroadcastReceiver = SmsBroadcastReceiver()
        registerReceiver(
            smsBroadcastReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )*/
    }

    override fun onTerminate() {
        //unregisterReceiver(smsBroadcastReceiver)
        super.onTerminate()
    }
}