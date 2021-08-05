package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.main.IOnBackPressed

class SearchFragment : BaseFragment<FragmentSearchBinding>(), IOnBackPressed {
    private val fragment: SearchMainFragment by lazy { SearchMainFragment() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            commit()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        childFragmentManager.fragments.last().onHiddenChanged(hidden)
    }

    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount >= 1) {
            childFragmentManager.popBackStackImmediate()
            return true
        }
        return false
    }

    fun openSearchHistoryFragment() {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, SearchHistoryFragment())
            addToBackStack(null)
            commit()
        }
    }

    fun openSearchResultFragment(keyword: String) {
        val fragment = SearchResultFragment.create(keyword)

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}