package com.doubleslas.fifith.alcohol.ui.record.cupboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentCupboardBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.sort.SortBottomSheetDialog
import com.doubleslas.fifith.alcohol.sort.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter.Companion.VIEW_TYPE_LOADING
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.record.RecordMenuBottomSheetDialog

class CupboardFragment : BaseFragment<FragmentCupboardBinding>() {
    private val viewModel by lazy { ViewModelProvider(this).get(CupboardViewModel::class.java) }
    private val adapter by lazy { CupboardAdapter() }
    private val loadingAdapter by lazy { LoadingRecyclerViewAdapter(adapter) }

    private val sortBottomSheetDialog by lazy {
        SortBottomSheetDialog(CupboardSortType.values()).apply {
            setOnSortSelectListener { viewModel.setSort(it) }
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCupboardBinding {
        return FragmentCupboardBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            val layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (loadingAdapter.getItemViewType(position) == VIEW_TYPE_LOADING) {
                        return layoutManager.spanCount
                    }
                    return 1
                }
            }

            it.recyclerview.layoutManager = layoutManager
            it.recyclerview.adapter = loadingAdapter

            it.layoutSort.root.setOnClickListener {
                sortBottomSheetDialog.show(fragmentManager!!, viewModel.cupboardSort.value!!)
            }
        }

        loadingAdapter.setOnBindLoadingListener {
            viewModel.loadCupboardList()
        }

        viewModel.cupboardSort.observe(viewLifecycleOwner, Observer {
            binding?.layoutSort?.tvSort?.text = it.text
        })

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    loadingAdapter.setVisibleLoading(!viewModel.isFinish())
                    adapter.setData(it.data)
                    loadingAdapter.notifyDataSetChanged()
                }
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                RecordMenuBottomSheetDialog().apply {
                    setOnItemClickListener { _, value ->
                        when (value) {
                            getString(R.string.record_delete) -> setDeleteMode()
                        }
                    }
                    show(fragmentManager!!, null)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setDeleteMode() {

    }

}