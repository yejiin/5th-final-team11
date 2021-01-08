package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchListBinding
import com.doubleslas.fifith.alcohol.enum.SortType
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_search_list.*

class SearchListFragment : BaseFragment<FragmentSearchListBinding>() {
    private val categoryList by lazy {
        listOf(
            Pair(getString(R.string.category_all), "전체"),
            Pair(getString(R.string.category_liquor), "양주"),
            Pair(getString(R.string.category_wine), "와인"),
            Pair(getString(R.string.category_beer), "세계맥주")
        )
    }
    private val adapter by lazy { ViewPagerAdapter() }
    private val fragmentList by lazy {
        List(categoryList.size) {
            AlcoholListFragment.create(categoryList[it].second)
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchListBinding {
        return FragmentSearchListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            b.viewPager.adapter = adapter

            TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                tab.text = categoryList[position].first
            }.attach()

            b.layoutSearch.setOnClickListener {
                startActivity(SearchActivity.getStartIntent(context!!))
            }
        }

    }


    fun setSort(fragment: Fragment, sortType: SortType) {
        for (f in fragmentList) {
            if (f == fragment) continue
            f.setSort(sortType)
        }
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}