package com.doubleslas.fifith.alcohol

import android.app.Application
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.doubleslas.fifith.alcohol.utils.SharedPreferenceUtil
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
        prefs = SharedPreferenceUtil(this)


        // Firebase SDK init
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    companion object {
        private lateinit var app: App
        lateinit var prefs: SharedPreferenceUtil
            private set

        fun getString(@StringRes id: Int): String {
            return app.getString(id)
        }

    }
}