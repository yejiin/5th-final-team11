package com.doubleslas.fifith.alcohol.ui.common

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.model.base.RestClient
import com.doubleslas.fifith.alcohol.ui.SplashActivity
import com.doubleslas.fifith.alcohol.ui.licence.LicenceActivity


open class ToolbarMenuBottomSheetDialog : BottomSheetMenu() {
    private val list by lazy {
        mutableListOf(
            getString(R.string.all_licence),
            getString(R.string.all_logout),
            getString(R.string.all_witdraw)
        )
    }
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
                    logout(requireActivity())
                }
                getString(R.string.all_witdraw) -> {
                    val activity = requireActivity()
                    AlertDialog.Builder(activity)
                        .setTitle("회원 탈퇴")
                        .setMessage("회원 탈퇴 하시겠습니까?")
                        .setPositiveButton("회원 탈퇴",
                            DialogInterface.OnClickListener { _, _ -> // 확인시 처리 로직
                                RestClient.getUserService().withdraw()
                                logout(activity)
                            })
                        .setNegativeButton("취소",
                            DialogInterface.OnClickListener { _, _ -> // 취소시 처리 로직
                            })
                        .show()

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

    fun logout(activity: Activity) {
        App.prefs.clear()

        val intent = Intent(activity, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)

        activity.finish()
    }
}