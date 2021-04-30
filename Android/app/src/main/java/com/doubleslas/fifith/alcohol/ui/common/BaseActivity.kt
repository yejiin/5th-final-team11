package com.doubleslas.fifith.alcohol.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.doubleslas.fifith.alcohol.ui.main.IOnBackPressed

open class BaseActivity : AppCompatActivity() {
    override fun onBackPressed() {
        if (onBackPressedFragment(supportFragmentManager)) return
        super.onBackPressed()
    }

    private fun onBackPressedFragment(fm: FragmentManager): Boolean {
        for (f in fm.fragments) {
            if (!f.isVisible) continue
            if (onBackPressedFragment(f.childFragmentManager)) return true
            if (f is IOnBackPressed) {
                if (f.onBackPressed()) return true
            }
        }
        return false
    }



}