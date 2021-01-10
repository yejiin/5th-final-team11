package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchResultBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.SearchList
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.ISortedPageLoader
import kotlinx.android.parcel.Parcelize

class SearchResultFragment private constructor() : BaseFragment<FragmentSearchResultBinding>() {
    private val keyword by lazy { arguments!!.getString(ARGUMENT_KEYWORD, "") }
    private val listFragment by lazy {
        AlcoholListFragment.create(SearchRepository.SearchPageLoader(keyword))
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchResultBinding {
        return FragmentSearchResultBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.tvTitle.text = keyword
            b.btnSearch.setOnClickListener {
                (parentFragment as? SearchFragment)?.openSearchHistoryFragment()
            }
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_list, listFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
        binding?.let { b ->
            setSupportActionBar(b.toolbar)
            getSupportActionBar()?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setHomeAsUpIndicator(R.drawable.ic_back_12)
                it.setDisplayShowTitleEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARGUMENT_KEYWORD = "ARGUMENT_KEYWORD"

        fun create(keyword: String): SearchResultFragment {
            val fragment = SearchResultFragment()
            fragment.arguments = Bundle().apply {
                putString(ARGUMENT_KEYWORD, keyword)
            }
            return fragment
        }
    }
}