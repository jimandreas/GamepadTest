package com.bammellab.gamepadtest

import android.app.Application
import android.content.Context
import com.bammellab.gamepadtest.util.PrefsUtil

class MainApplication : Application() {

    private lateinit var appContext : Context

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        PrefsUtil.prefsContext = appContext
    }
}