package com.doubleslas.fifith.alcohol

import android.app.Application
import com.doubleslas.fifith.alcohol.utils.SharedPreferenceUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferenceUtil(this)
    }

    companion object {
        lateinit var prefs: SharedPreferenceUtil
            private set

    }
}