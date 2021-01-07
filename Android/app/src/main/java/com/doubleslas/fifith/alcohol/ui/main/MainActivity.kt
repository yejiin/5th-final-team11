package com.doubleslas.fifith.alcohol.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityMainBinding
import com.doubleslas.fifith.alcohol.ui.search.SearchMainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val searchFragment by lazy { SearchMainFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        gotoSearch()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_recommend -> gotoRecommend()
            R.id.menu_search -> gotoSearch()
            R.id.menu_record -> gotoSearch()
            R.id.menu_stats -> gotoStats()
            else -> return false
        }

        return true
    }


    private fun gotoSearch() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, searchFragment)
            .commit()
    }

    private fun gotoRecommend() {

    }

    private fun gotoRecord() {

    }

    private fun gotoStats() {

    }
}