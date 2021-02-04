package com.doubleslas.fifith.alcohol.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailInfoBinding
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailReviewBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewBottomSheetDialog
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel
import com.doubleslas.fifith.alcohol.viewmodel.ReviewViewModel

class DetailReviewFragment : Fragment() {
    private var _binding: FragmentDetailReviewBinding? = null
    private val binding: FragmentDetailReviewBinding get() = _binding!!
    private var reviewViewModel: ReviewViewModel? = null
    private val adapter by lazy { DetailReviewAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailReviewBinding.inflate(inflater, container, false)

        binding.rvDetailReview.adapter = adapter

        binding.rvDetailReview.layoutManager = LinearLayoutManager(AlcoholDetailActivity()).also {
            it.orientation = LinearLayoutManager.VERTICAL
        }


        binding.rvDetailReview.addItemDecoration(DetailReviewDecoration(5))


        reviewViewModel?.readReview(6, 0)?.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    adapter.setData(it.data.reviewList)
                }
                is ApiStatus.Error -> {

                }

            }
        })

        binding.btnWriteReview.setOnClickListener {
            val bottomSheet =
                ReviewBottomSheetDialog.create(6)

            bottomSheet.onListener {
                reviewViewModel?.readReview(6, 0)?.observe(this, Observer {
                    when (it) {
                        is ApiStatus.Loading -> {

                        }
                        is ApiStatus.Success -> {
                            adapter.setData(it.data.reviewList)
                        }
                        is ApiStatus.Error -> {

                        }

                    }
                })
            }

            // 다음 작업에
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewViewModel =  ViewModelProvider(AlcoholDetailActivity()).get(ReviewViewModel::class.java)
    }
}

