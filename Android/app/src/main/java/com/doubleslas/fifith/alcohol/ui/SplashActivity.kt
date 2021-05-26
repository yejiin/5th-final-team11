package com.doubleslas.fifith.alcohol.ui

import android.animation.Animator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.databinding.ActivitySplashBinding
import com.doubleslas.fifith.alcohol.model.FirebaseConfig
import com.doubleslas.fifith.alcohol.ui.auth.AgeCheckActivity
import com.doubleslas.fifith.alcohol.ui.auth.CustomDialog
import com.doubleslas.fifith.alcohol.ui.auth.CustomDialogInterface
import com.doubleslas.fifith.alcohol.ui.auth.LoginActivity
import com.doubleslas.fifith.alcohol.ui.auth.recommendinfo.RecommendInfoActivity
import com.doubleslas.fifith.alcohol.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val packageInfo =
            applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)
        val versionCode =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        val minVersion = FirebaseConfig.getMinVersion()

        if (versionCode < minVersion) {
            CustomDialog(this, "버전 업데이트가 필요합니다.").apply {
                customDialogInterface = object : CustomDialogInterface {
                    override fun onConfirmBtnClicked() {
                        // TODO : Play Store Open
                        dismiss()
                        finish()
                    }
                }
                show()
            }
            return
        }

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val intent = Intent(
                    applicationContext,
                    when {
                        !App.isLogin() -> AgeCheckActivity::class.java
                        !App.prefs.submitRecommendInfo -> LoginActivity::class.java
                        !App.prefs.registerUserInfo -> RecommendInfoActivity::class.java
                        else -> MainActivity::class.java
                    }
                )
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

}