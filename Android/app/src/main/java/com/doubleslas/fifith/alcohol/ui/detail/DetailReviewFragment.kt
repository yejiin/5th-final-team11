package com.doubleslas.fifith.alcohol.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailReviewBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewBottomSheetDialog

class DetailReviewFragment : BaseFragment<FragmentDetailReviewBinding>() {
    private val reviewViewModel by lazy { ViewModelProvider(activity!!).get(DetailViewModel::class.java) }
    private val adapter by lazy { DetailReviewAdapter() }
    private val loadingAdapter by lazy { LoadingRecyclerViewAdapter(adapter) }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailReviewBinding {
        return FragmentDetailReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.rvDetailReview.adapter = loadingAdapter

            b.rvDetailReview.layoutManager = LinearLayoutManager(AlcoholDetailActivity()).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }

            b.btnWriteReview.setOnClickListener {
                val bottomSheet =
                    ReviewBottomSheetDialog.create(reviewViewModel.aid)

                bottomSheet.onListener {
                    reviewViewModel.resetReview()
                    reviewViewModel.loadReview()
                }

                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        }


        loadingAdapter.setOnBindLoadingListener {
            reviewViewModel.loadReview()
        }
        reviewViewModel.reviewLiveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                    loadingAdapter.notifyDataSetChanged()

                    loadingAdapter.setVisibleLoading(!reviewViewModel.isFinishReview())
                }
                is ApiStatus.Error -> {

                }

            }
        })
    }


    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}

