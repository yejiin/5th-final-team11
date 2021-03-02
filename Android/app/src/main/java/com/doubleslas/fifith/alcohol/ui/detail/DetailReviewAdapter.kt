package com.doubleslas.fifith.alcohol.ui.detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemDetailReviewBinding
import com.doubleslas.fifith.alcohol.dto.ReviewData
import com.doubleslas.fifith.alcohol.ui.reivew.ReportBottomSheetDialog

class DetailReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ReviewData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            ItemDetailReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    fun setData(list: List<ReviewData>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ReviewViewHolder) return
        val item = list!![position]
        with(holder.binding) {
            tvNickname.text = item.nickname
            tvReview.text = item.content
            reviewRating.rating = item.star
            tvLikeCount.text = item.love.toString()

            if (item.detail != null) {
                layoutReview.seekBarHangover.seekBar.progress = item.detail.hangover!!
                layoutReview.etPlace.setText(item.detail.place)
                layoutReview.etPrice.setText(item.detail.price.toString())
                layoutReview.etCalendar.setText(item.detail.date)
                layoutReview.etDrink.setText(item.detail.drink.toString())
            } else {
                layoutReview.layoutDetailReview.visibility = View.GONE
            }


            btnComment.setOnClickListener {
                // 댓글 달기 버튼
                layoutReviewComment.visibility = View.VISIBLE
                btnComment.setTextColor(Color.parseColor("#FFFFFF"))
            }

            btnCommentConfirm.setOnClickListener {
                // 리뷰 댓글 등록
            }

            btnCommentCancel.setOnClickListener {
                // 리뷰 댓글 등록 취소
                layoutReviewComment.visibility = View.GONE
                btnComment.setTextColor(Color.parseColor("#A5A5A5"))
            }

            layoutLike.setOnClickListener {
                // 리뷰 좋아요 버튼 눌렀을 때의 처리
                if (item.isLove) {
                    ivReviewLike.setImageResource(R.drawable.ic_heart_gray)
                } else {
                    ivReviewLike.setImageResource(R.drawable.ic_heart_orange)
                }
            }

            btnReport.setOnClickListener {
                // 신고하기 버튼 눌렀을때의 처리
                val appCompatActivity = AppCompatActivity()
                val fragmentManager = appCompatActivity.supportFragmentManager
                val bottomSheet = ReportBottomSheetDialog()
                bottomSheet.show(fragmentManager, bottomSheet.tag)

            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ReviewViewHolder(var binding: ItemDetailReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding.layoutReview) {
                etCalendar.isEnabled = false
                etDrink.isEnabled = false
                etPlace.isEnabled = false
                etPrice.isEnabled = false
                seekBarHangover.seekBar.isEnabled = false
                seekBarHangover.tvLabel1.text = "없음"
                seekBarHangover.tvLabel2.text = "심함"
            }
        }
    }
}