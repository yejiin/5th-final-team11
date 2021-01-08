package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentSearchListBinding
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment

class SearchHistoryFragment : BaseFragment<RecyclerviewBinding>() {
    private val adapter by lazy { SearchHistoryAdapter() }

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
    }

}