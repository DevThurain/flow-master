package com.development.flowmaster

import android.app.Application
import timber.log.Timber

class FlowMasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}