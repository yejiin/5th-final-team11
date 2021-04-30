package com.doubleslas.fifith.alcohol.ui.detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailReviewBinding
import com.doubleslas.fifith.alcohol.dto.ReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.main.IOnBackPressed
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewBottomSheetDialog


class DetailReviewFragment : BaseFragment<FragmentDetailReviewBinding>() {
    private val reviewViewModel by lazy { ViewModelProvider(activity!!).get(DetailViewModel::class.java) }
    private val adapter by lazy { DetailReviewAdapter() }
    private val loadingAdapter by lazy { LoadingRecyclerViewAdapter(adapter) }
    private val writeDialog by lazy { ReviewBottomSheetDialog.create(reviewViewModel.aid) }


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
                writeDialog.onListener {
                    reviewViewModel.resetReview()
                    reviewViewModel.loadReview()
                }

                writeDialog.show(childFragmentManager, writeDialog.tag)
            }
        }


        adapter.setListener(object : DetailReviewAdapter.ReviewItemListener {
            override fun comment(position: Int, item: ReviewData, comment: String) {
                reviewViewModel.commentReview(item, comment)?.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is ApiStatus.Success -> {
                            loadingAdapter.notifyItemChanged(position)
                        }
                        is ApiStatus.Error -> {

                        }
                        is ApiStatus.ValidateFail -> {

                        }
                    }
                })
            }

            override fun like(position: Int, item: ReviewData, value: Boolean) {
                reviewViewModel.likeReview(item, value)?.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is ApiStatus.Success -> {
                            loadingAdapter.notifyItemChanged(position)
                        }
                        is ApiStatus.Error -> {
                            loadingAdapter.notifyItemChanged(position)
                        }
                    }
                })
            }

        })

        loadingAdapter.setOnBindLoadingListener {
            reviewViewModel.loadReview()
        }

        reviewViewModel.reviewLiveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    binding?.layoutReviewEmpty?.isVisible = it.data.isEmpty()

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

