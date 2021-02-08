package com.doubleslas.fifith.alcohol.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private var fragmentList = listOf(DetailInfoFragment(), DetailReviewFragment())
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentList[position]
        return fragment
    }
}