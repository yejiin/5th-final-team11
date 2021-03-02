package com.doubleslas.fifith.alcohol.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailReviewBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.LoadingRecyclerViewAdapter
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewBottomSheetDialog

class DetailReviewFragment : Fragment() {
    private lateinit var binding: FragmentDetailReviewBinding
    private val reviewViewModel by lazy { ViewModelProvider(activity!!).get(DetailViewModel::class.java) }
    private val adapter by lazy { DetailReviewAdapter() }
    private val loadingAdapter by lazy { LoadingRecyclerViewAdapter(adapter) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailReviewBinding.inflate(inflater, container, false)

        binding.rvDetailReview.adapter = adapter

        binding.rvDetailReview.layoutManager = LinearLayoutManager(AlcoholDetailActivity()).also {
            it.orientation = LinearLayoutManager.VERTICAL
        }


        binding.rvDetailReview.addItemDecoration(DetailReviewDecoration(5))



        binding.btnWriteReview.setOnClickListener {
            val bottomSheet =
                ReviewBottomSheetDialog.create(reviewViewModel.aid)

            bottomSheet.onListener {
                reviewViewModel.resetReview()
                reviewViewModel.loadReview()
            }

            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }


        reviewViewModel.loadReview()
        reviewViewModel.reviewLiveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                }
                is ApiStatus.Error -> {

                }

            }
        })

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}

