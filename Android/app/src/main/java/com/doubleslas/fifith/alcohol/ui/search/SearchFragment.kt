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

        binding?.let {
            it.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.viewPager.adapter = ViewPagerAdapter()

            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                tab.text = categoryList[position].first
            }.attach()

            it.tvSort.text = getString(R.string.sort_popular)
            it.tvSort.setOnClickListener {
                sortDialog.show(fragmentManager!!, null)
            }
        }

        sortDialog.setOnSortSelectListener {

        }
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(activity!!) {
        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun createFragment(position: Int): Fragment {
            return AlcoholListFragment.create(categoryList[position].second)
        }
    }
}