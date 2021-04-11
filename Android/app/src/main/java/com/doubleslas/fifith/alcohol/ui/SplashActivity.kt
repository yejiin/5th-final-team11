package com.doubleslas.fifith.alcohol.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.databinding.ActivitySplashBinding
import com.doubleslas.fifith.alcohol.ui.auth.AgeCheckActivity
import com.doubleslas.fifith.alcohol.ui.auth.LoginActivity
import com.doubleslas.fifith.alcohol.ui.auth.Register2Activity
import com.doubleslas.fifith.alcohol.ui.auth.RegisterActivity
import com.doubleslas.fifith.alcohol.ui.auth.recommendinfo.RecommendInfoActivity
import com.doubleslas.fifith.alcohol.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val intent = Intent(
                    applicationContext,
                    when {
                        !App.isLogin() -> AgeCheckActivity::class.java
                        App.prefs.submitRecommendInfo -> LoginActivity::class.java
                        App.prefs.registerUserInfo -> RecommendInfoActivity::class.java
                        else -> AgeCheckActivity::class.java
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