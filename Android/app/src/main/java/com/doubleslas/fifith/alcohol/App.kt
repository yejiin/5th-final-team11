package com.doubleslas.fifith.alcohol

import android.app.Application
import com.doubleslas.fifith.alcohol.utils.SharedPreferenceUtil
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferenceUtil(this)


        // Firebase SDK init
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    companion object {
        lateinit var prefs: SharedPreferenceUtil
            private set

    }
}