package com.doubleslas.fifith.alcohol.utils

import android.content.Context

class SharedPreferenceUtil(private val context: Context) {
    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var idToken: String
        get() = prefs.getString(PREFS_ID_TOKEN, "") ?: ""
        set(value) = prefs.edit().putString(PREFS_ID_TOKEN, value).apply()

    var searchHistoryList: String
        get() = prefs.getString(PREFS_SEARCH_HISTORY_LIST, "[]") ?: "{}"
        set(value) = prefs.edit().putString(PREFS_SEARCH_HISTORY_LIST, value).apply()

    var registerUserInfo: Boolean
        get() = prefs.getBoolean(PREFS_REGISTER_USER_INFO, false)
        set(value) = prefs.edit().putBoolean(PREFS_REGISTER_USER_INFO, value).apply()

    var submitRecommendInfo: Boolean
        get() = prefs.getBoolean(PREFS_SUBMIT_RECOMMEND_INFO, false)
        set(value) = prefs.edit().putBoolean(PREFS_SUBMIT_RECOMMEND_INFO, value).apply()

    companion object {
        private const val PREFS_NAME = "DoubleSlash05_team11_final_alcohol"

        private const val PREFS_ID_TOKEN = "PREFS_ID_TOKEN"
        private const val PREFS_SEARCH_HISTORY_LIST = "PREFS_SEARCH_HISTORY_LIST"
        private const val PREFS_REGISTER_USER_INFO = "PREFS_REGISTER_USER_INFO"
        private const val PREFS_SUBMIT_RECOMMEND_INFO = "PREFS_SUBMIT_RECOMMEND_INFO"
    }
}