package com.doubleslas.fifith.alcohol.ui.common

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.BuildConfig
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.SplashActivity
import com.doubleslas.fifith.alcohol.ui.licence.LicenceActivity


open class ToolbarMenuBottomSheetDialog : BottomSheetMenu() {
    private val list = mutableListOf(
        getString(R.string.all_licence),
        getString(R.string.all_logout),
        getString(R.string.all_witdraw)
    )
    private var addOnItemClickListener: ((String) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(
            list
        )

        setOnItemClickListener { _, value ->
            when (value) {
                getString(R.string.all_licence) -> {
                    val intent = Intent(context, LicenceActivity::class.java)
                    startActivity(intent)
                }
                getString(R.string.all_logout) -> {
                    App.prefs.clear()

                    val intent = Intent(context, SplashActivity::class.java)
                    startActivity(intent)

                    activity?.finish()
                }
                getString(R.string.all_witdraw) -> {
                    // TOOD: 회원 탈퇴 API
                }
                else -> {
                    addOnItemClickListener?.invoke(value)
                }
            }
        }
    }

    open fun addList(index: Int, str: String) {
        list.add(index, str)
    }

    fun addOnItemClickListener(listener: (String) -> Unit) {
        addOnItemClickListener = listener
    }

}