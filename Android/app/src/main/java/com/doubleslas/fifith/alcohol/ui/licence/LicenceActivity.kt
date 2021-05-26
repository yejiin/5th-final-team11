package com.doubleslas.fifith.alcohol.ui.licence

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding

class LicenceActivity: AppCompatActivity() {
    private lateinit var binding: RecyclerviewBinding

    override fun setContentView(view: View?) {
        super.setContentView(view)
        binding = RecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}