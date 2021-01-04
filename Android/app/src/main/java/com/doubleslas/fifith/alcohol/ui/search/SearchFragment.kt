package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchBinding
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListFragment
import com.doubleslas.fifith.alcohol.ui.common.SortBottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val sortDialog by lazy { SortBottomSheetDialog() }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            b.viewPager.adapter = adapter

            TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                tab.text = categoryList[position].first
            }.attach()

            b.tvSort.text = getString(R.string.sort_popular)
            b.tvSort.setOnClickListener {
                sortDialog.show(fragmentManager!!, null)
            }

            sortDialog.setOnSortSelectListener {
                for (f in fragmentList) {
                    f.setSort(it)
                }
            }
        }

    }

    inner class ViewPagerAdapter : FragmentStateAdapter(activity!!) {
        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}