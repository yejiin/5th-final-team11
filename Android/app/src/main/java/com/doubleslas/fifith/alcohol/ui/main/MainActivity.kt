package com.doubleslas.fifith.alcohol.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityMainBinding
import com.doubleslas.fifith.alcohol.ui.recommend.RecommendFragment
import com.doubleslas.fifith.alcohol.ui.record.RecordFragment
import com.doubleslas.fifith.alcohol.ui.search.SearchFragment
import com.doubleslas.fifith.alcohol.ui.stats.StatsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val recommendFragment by lazy { RecommendFragment() }
    private val searchFragment by lazy { SearchFragment() }
    private val recordFragment by lazy { RecordFragment() }
    private val statsFragment by lazy { StatsFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment, recommendFragment)
            .add(R.id.main_fragment, searchFragment)
            .add(R.id.main_fragment, recordFragment)
            .add(R.id.main_fragment, statsFragment)
            .commit()

        gotoRecommend()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        hideAllFragment()

        when (item.itemId) {
            R.id.menu_recommend ->
                supportFragmentManager.beginTransaction()
                    .show(recommendFragment)
                    .commit()
            R.id.menu_search ->
                supportFragmentManager.beginTransaction()
                    .show(searchFragment)
                    .commit()
            R.id.menu_record ->
                supportFragmentManager.beginTransaction()
                    .show(recordFragment)
                    .commit()
            R.id.menu_stats ->
                supportFragmentManager.beginTransaction()
                    .show(statsFragment)
                    .commit()
            else -> return false
        }

        return true
    }

    override fun onBackPressed() {
        if (searchFragment.childFragmentManager.backStackEntryCount >= 1) {
            searchFragment.childFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }


    private fun gotoRecommend() {
        binding.bottomNavigation.selectedItemId = R.id.menu_recommend
    }

    private fun gotoSearch() {
        binding.bottomNavigation.selectedItemId = R.id.menu_search
    }

    private fun gotoRecord() {
        binding.bottomNavigation.selectedItemId = R.id.menu_record
    }

    private fun gotoStats() {
        binding.bottomNavigation.selectedItemId = R.id.menu_stats
    }

    private fun hideAllFragment() {
        supportFragmentManager.beginTransaction()
            .hide(recommendFragment)
            .hide(searchFragment)
            .hide(recordFragment)
            .hide(statsFragment)
            .commit()
    }
}