package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchResultBinding
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment

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

        binding?.let {
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_list, listFragment)
            .addToBackStack(null)
            .commit()
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