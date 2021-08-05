package com.doubleslas.fifith.alcohol.ui.licence

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding

class LicenceActivity: AppCompatActivity() {
    private lateinit var binding: RecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = LicenceAdapter()

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter
    }

}