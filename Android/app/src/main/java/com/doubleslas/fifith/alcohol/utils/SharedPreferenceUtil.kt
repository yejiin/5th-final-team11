package com.doubleslas.fifith.alcohol.utils

import android.content.Context
import com.doubleslas.fifith.alcohol.App

class SharedPreferenceUtil(private val context: Context) {
    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isShowTutorial: Boolean
        get() = prefs.getBoolean(PREFS_IS_SHOW_TUTORIAL, true)
        set(value) = prefs.edit().putBoolean(PREFS_IS_SHOW_TUTORIAL, value).apply()

    companion object {
        private const val PREFS_NAME = "DoubleSlash05_team11_final_alcohol"

        private const val PREFS_IS_SHOW_TUTORIAL = "PREFS_IS_SHOW_TUTORIAL"
    }
}