package com.doubleslas.fifith.alcohol.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivitySearchBinding
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity
import kotlinx.android.synthetic.main.activity_search.view.*

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val historyFragment by lazy { SearchHistoryFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction()
            .replace(R.id.search_fragment, historyFragment)
            .commit()
        
    }

    companion object{
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }
}