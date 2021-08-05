package com.doubleslas.fifith.alcohol.ui.record.myreview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentMyReviewBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.licence.LicenceActivity
import com.doubleslas.fifith.alcohol.ui.main.IOnBackPressed
import com.doubleslas.fifith.alcohol.ui.record.RecordMenuBottomSheetDialog

class MyReviewFragment : BaseFragment<FragmentMyReviewBinding>(), IOnBackPressed {
    private val viewModel by lazy { ViewModelProvider(this).get(MyReviewViewModel::class.java) }
    private val adapter by lazy {
        MyReviewAdapter().apply {
            setOnChangeSortListener {
                viewModel.setSort(it)
            }
        }
    }
    private val loadingAdapter by lazy { LoadingRecyclerViewAdapter(adapter) }

    private val menuBottomSheetDialog by lazy {
        RecordMenuBottomSheetDialog().apply {
            setOnItemClickListener { _, value ->
                when (value) {
                    getString(R.string.record_delete) -> setDeleteMode(true)
                }
            }
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyReviewBinding {
        return FragmentMyReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.recyclerview.layoutManager = LinearLayoutManager(context)
            it.recyclerview.adapter = loadingAdapter
            it.btnDelete.setOnClickListener {
                viewModel.deleteList().observe(viewLifecycleOwner, Observer { })
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                menuBottomSheetDialog.show(fragmentManager!!, null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        if (!viewModel.deleteMode) return false
        setDeleteMode(false)
        return true
    }

    private fun setDeleteMode(value: Boolean) {
        viewModel.deleteMode = value
        binding?.let { b ->
            if (viewModel.deleteMode) {
                b.btnDelete.visibility = View.VISIBLE
                adapter.setSelectMode(true)
                loadingAdapter.notifyDataSetChanged()
            } else {
                b.btnDelete.visibility = View.GONE
                adapter.setSelectMode(false)
                loadingAdapter.notifyDataSetChanged()
            }
        }
    }
}
