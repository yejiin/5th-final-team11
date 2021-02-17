package com.doubleslas.fifith.alcohol.ui.record.myreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment

class MyReviewFragment : BaseFragment<RecyclerviewBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(MyReviewViewModel::class.java) }
    private val adapter by lazy { MyReviewAdapter() }
    private val loadingAdapter by lazy { LoadingRecyclerViewAdapter(adapter) }


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
            it.recyclerview.adapter = loadingAdapter
        }

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            loadingAdapter.setVisibleLoading(!viewModel.isFinishList())
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                    loadingAdapter.notifyDataSetChanged()
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadList()
    }
}
