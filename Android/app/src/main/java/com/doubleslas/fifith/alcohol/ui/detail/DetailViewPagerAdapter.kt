package com.doubleslas.fifith.alcohol.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private var fragmentList = listOf<Fragment>(DetailInfoFragment(), DetailReviewFragment())
    override fun getItemCount(): Int {
        return fragmentList.count()
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}