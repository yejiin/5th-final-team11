package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListAdapter
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.SearchListViewModel

class SearchListFragment private constructor() : BaseFragment<RecyclerviewBinding>() {
    private val category by lazy { arguments?.getString(ARGUMENT_CATEGORY) ?: "전체" }
    private val viewModel by lazy {
        ViewModelProvider(this, SearchListViewModel.Factory(category))
            .get(SearchListViewModel::class.java)
    }
    private val adapter by lazy { AlcoholListAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): RecyclerviewBinding {
        return RecyclerviewBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.recyclerview.layoutManager = LinearLayoutManager(context)
            it.recyclerview.adapter = adapter
        }

        adapter.setOnSortChangeListener {
            viewModel.setSort(it)
        }

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.initList()) adapter.setSort(viewModel.sort)
    }

    companion object {
        private const val ARGUMENT_CATEGORY = "ARGUMENT_CATEGORY"

        fun create(category: String): SearchListFragment {
            val fragment = SearchListFragment()
            fragment.arguments = Bundle().apply {
                putString(ARGUMENT_CATEGORY, category)
            }
            return fragment
        }
    }
}