package com.doubleslas.fifith.alcohol.ui.detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemDetailReviewBinding
import com.doubleslas.fifith.alcohol.dto.ReviewData
import com.doubleslas.fifith.alcohol.ui.reivew.ReportBottomSheetDialog

class DetailReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ReviewData>? = null
    private var listener: ReviewItemListener? = null

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
            tvDate.text = item.reviewDate
            tvReview.text = item.content
            reviewRating.rating = item.star
            if (item.isLove == null) {
                checkboxLike.visibility = View.GONE
            } else {
                checkboxLike.visibility = View.VISIBLE
                checkboxLike.isChecked = item.isLove!!
            }
            checkboxLike.text = item.love.toString()
            etReviewComment.setText(item.cacheComment)

            if (item.comments.isEmpty()) {
                btnCommentList.visibility = View.GONE
            } else {
                btnCommentList.text =
                    if (item.comments.size > 99) "+99" else item.comments.size.toString()
                btnCommentList.visibility = View.VISIBLE
            }

            if (item.detail != null) {
                layoutReview.seekBarHangover.seekBar.progress = item.detail.hangover!!
                layoutReview.etPlace.setText(item.detail.place)
                layoutReview.etPrice.setText(item.detail.price.toString())
                layoutReview.etCalendar.setText(item.detail.date)
                layoutReview.etDrink.setText(item.detail.drink.toString())
            } else {
                layoutReview.layoutDetailReview.visibility = View.GONE
            }

            btnReport.setOnClickListener {
                // 신고하기 버튼 눌렀을때의 처리
                val appCompatActivity = AppCompatActivity()
                val fragmentManager = appCompatActivity.supportFragmentManager
                val bottomSheet = ReportBottomSheetDialog()
                bottomSheet.show(fragmentManager, bottomSheet.tag)
            }
        }

        holder.refreshComment()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun setListener(listener: ReviewItemListener) {
        this.listener = listener
    }

    interface ReviewItemListener {
        fun comment(item: ReviewData, comment: String)
        fun like(item: ReviewData, value: Boolean)
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

            // Comment
            binding.btnComment.setOnClickListener {
                val item = list!![adapterPosition]
                item.visibleComment = !item.visibleComment
                refreshComment()
            }

            binding.btnCommentCancel.setOnClickListener {
                val item = list!![adapterPosition]
                item.visibleComment = false
                item.cacheComment = ""
                binding.etReviewComment.setText("")
                refreshComment()
            }

            binding.btnCommentConfirm.setOnClickListener {
                val item = list!![adapterPosition]
                listener?.comment(item, binding.etReviewComment.text.toString())
            }

            binding.checkboxLike.setOnCheckedChangeListener { _, isChecked ->
                val item = list!![adapterPosition]
                listener?.like(item, isChecked)
            }

            binding.etReviewComment.addTextChangedListener {
                val item = list!![adapterPosition]
                item.cacheComment = it.toString()
            }

            binding.btnCommentList.setOnClickListener {
                val item = list!![adapterPosition]
                item.visibleCommentList = !item.visibleCommentList
                refreshCommentList()
            }
        }

        fun refreshCommentList() {
            val item = list!![adapterPosition]
            if (item.visibleCommentList) {
                binding.layoutCommentList.visibility = View.VISIBLE
            } else {
                binding.layoutCommentList.visibility = View.GONE
            }
        }

        fun refreshComment() {
            val item = list!![adapterPosition]
            if (item.visibleComment) {
                binding.layoutReviewComment.visibility = View.VISIBLE
                binding.btnComment.setTextColor(Color.WHITE)
            } else {
                binding.layoutReviewComment.visibility = View.GONE
                binding.btnComment.setTextColor(Color.parseColor("#A5A5A5"))
            }
        }
    }
}