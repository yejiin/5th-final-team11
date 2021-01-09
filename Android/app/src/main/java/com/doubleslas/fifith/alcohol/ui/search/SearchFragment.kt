package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val fragmentList by lazy { SearchMainFragment() }
    private val fragmentHistory by lazy { SearchHistoryFragment() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragmentList)
            commit()
        }
    }

    fun openSearchHistoryFragment() {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragmentHistory)
            addToBackStack(null)
            commit()
        }
    }
}