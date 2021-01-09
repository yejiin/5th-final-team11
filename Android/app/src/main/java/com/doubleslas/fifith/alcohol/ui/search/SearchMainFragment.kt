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
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.model.network.dto.SearchList
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.ISortedPageLoader
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.parcel.Parcelize

class SearchMainFragment : BaseFragment<FragmentSearchListBinding>() {
    private val categoryList by lazy {
        listOf(
            Pair(getString(R.string.category_all), "전체"),
            Pair(getString(R.string.category_liquor), "양주"),
            Pair(getString(R.string.category_wine), "와인"),
            Pair(getString(R.string.category_beer), "세계맥주")
        )
    }
    private lateinit var adapter: CategoryViewPagerAdapter
    private val fragmentList by lazy {
        List(categoryList.size) {
            val category = categoryList[it].second
            AlcoholListFragment.create(CategoryPageLoader(category))
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
        adapter = CategoryViewPagerAdapter()

        binding?.let { b ->
            b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            b.viewPager.adapter = adapter

            TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                tab.text = categoryList[position].first
            }.attach()

            b.layoutSearch.setOnClickListener {
                (parentFragment as? SearchFragment)?.openSearchHistoryFragment()
//                startActivity(SearchHistoryActivity.getStartIntent(context!!))
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.let { b ->
            setSupportActionBar(b.toolbar)
            getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        }
    }

    fun setSort(fragment: Fragment, sortType: SortType) {
        for (f in fragmentList) {
            if (f == fragment) continue
            f.setSort(sortType)
        }
    }

    inner class CategoryViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    @Parcelize
    class CategoryPageLoader(private val category: String) :
        ISortedPageLoader<SearchList> {
        private val repository = SearchRepository()
        override fun loadList(
            page: Int,
            sort: String,
            sortOption: String
        ): ApiLiveData<SearchList> {
            return repository.getList(category, page, sort, sortOption)
        }
    }
}