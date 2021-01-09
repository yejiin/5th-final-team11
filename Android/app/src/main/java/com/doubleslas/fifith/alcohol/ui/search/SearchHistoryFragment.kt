package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchHistoryBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.SearchViewModel

class SearchHistoryFragment : BaseFragment<FragmentSearchHistoryBinding>() {
    private val adapter by lazy { SearchHistoryAdapter() }
    private val vm by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchHistoryBinding {
        return FragmentSearchHistoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding?.let { b ->


            b.etSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                    return@setOnEditorActionListener true
                }

                return@setOnEditorActionListener false
            }

            b.btnSearch.setOnClickListener {
                search()
            }

            b.rvSearchHistory.layoutManager = LinearLayoutManager(context)
            b.rvSearchHistory.adapter = adapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.let { b ->
            setSupportActionBar(b.toolbar)
            getSupportActionBar()?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        adapter.setList(vm.getHistoryList())
        binding?.etSearch?.requestFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun search() {
        if (binding == null) return
        val keyword = binding!!.etSearch.text.toString()

        if (keyword.isEmpty()) {
            return
        }

        vm.search(keyword)
        (parentFragment as? SearchFragment)?.openSearchResultFragment(keyword)
    }

}