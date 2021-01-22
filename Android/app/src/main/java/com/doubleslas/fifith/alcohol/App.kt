package com.doubleslas.fifith.alcohol

import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import com.doubleslas.fifith.alcohol.utils.SharedPreferenceUtil
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.auth.internal.IdTokenListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
        prefs = SharedPreferenceUtil(this)

        Firebase.auth.addIdTokenListener(IdTokenListener {
            prefs.idToken = it.token ?: ""
        })


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

        fun showKeyboard(view: View?) {
            val imm: InputMethodManager? =
                app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(view, 0)
        }

        fun hideKeyboard(edit: EditText?) {
            val imm: InputMethodManager? =
                app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(edit?.windowToken, 0)
        }

        fun isLogin(): Boolean {
            return prefs.idToken.isNotEmpty()
        }

    }
}