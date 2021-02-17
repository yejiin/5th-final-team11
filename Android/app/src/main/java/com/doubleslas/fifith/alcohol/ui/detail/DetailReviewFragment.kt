package com.doubleslas.fifith.alcohol.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailReviewBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewBottomSheetDialog
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewViewModel

class DetailReviewFragment : Fragment() {
    private lateinit var binding: FragmentDetailReviewBinding
    private val reviewViewModel by lazy { ReviewViewModel() }
    private val adapter by lazy { DetailReviewAdapter() }


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


        reviewViewModel?.readReview(148, 0)?.observe(this, Observer {
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
                ReviewBottomSheetDialog.create(148)

            bottomSheet.onListener {
                reviewViewModel?.readReview(148, 0)?.observe(this, Observer {
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
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}

