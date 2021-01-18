package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemDetailReviewBinding
import com.doubleslas.fifith.alcohol.model.network.dto.DetailReviewData
import kotlinx.android.synthetic.main.item_alcohol_similar.view.*
import kotlinx.android.synthetic.main.item_detail_review.view.*

class DetailReviewAdapter(val context: Context): RecyclerView.Adapter<DetailReviewAdapter.ReviewViewHolder>() {
    var items: MutableList<DetailReviewData> = mutableListOf(
        DetailReviewData("닉네임1", "리뷰 입니다1", 3.5f),
        DetailReviewData("닉네임2", "리뷰 입니다2", 4.0f),
        DetailReviewData("닉네임3", "리뷰 입니다3", 1.2f),
        DetailReviewData("닉네임4", "리뷰 입니다4", 2.4f),
        DetailReviewData("닉네임5", "리뷰 입니다5", 5.0f),
        DetailReviewData("닉네임6", "리뷰 입니다6", 1.5f),
        DetailReviewData("닉네임7", "리뷰 입니다7", 2.5f)


    )


    inner class ReviewViewHolder(var binding: ItemDetailReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val nickname = binding.tvDetailNickname
        val review = binding.tvDetailReview
        val rating = binding.reviewRating
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemDetailReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        items[position].let {item ->
            with(holder) {
                nickname.text = item.nickname
                review.text = item.review
                rating.rating = item.rating

                binding.layoutDetailReview.etCalendar.isEnabled = false
                binding.layoutDetailReview.etDrink.isEnabled = false
                binding.layoutDetailReview.etPlace.isEnabled = false
                binding.layoutDetailReview.etPrice.isEnabled = false
                binding.layoutDetailReview.seekBarHangover.seekBar.isEnabled = false
                binding.layoutDetailReview.seekBarHangover.tvLabel1.text = "없음"
                binding.layoutDetailReview.seekBarHangover.tvLabel2.text = "심함"

            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}