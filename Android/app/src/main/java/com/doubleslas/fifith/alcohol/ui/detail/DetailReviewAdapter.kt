package com.doubleslas.fifith.alcohol.ui.detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemDetailReviewBinding
import com.doubleslas.fifith.alcohol.databinding.ItemReviewCommentBinding
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
            checkboxLike.text = item.loveCount.toString()
            etReviewComment.setText(item.cacheComment)

            if (item.comments.isEmpty()) {
                btnCommentList.visibility = View.GONE
            } else {
                btnCommentList.text = item.commentCount.toString()
                btnCommentList.visibility = View.VISIBLE

                val inflater = LayoutInflater.from(root.context)
                for (index in item.comments.indices) {
                    if (holder.commentHolder.size <= index) {
                        val b =
                            ItemReviewCommentBinding.inflate(inflater, layoutCommentList, false)
                        holder.commentHolder.add(b)
                        layoutCommentList.addView(b.root)
                    }

                    val c = item.comments[index]
                    val layout = holder.commentHolder[index]

                    layout.root.visibility = View.VISIBLE
                    layout.tvNickname.text = c.nickname
                    layout.tvDate.text = c.commentDate
                    layout.tvContent.text = c.content
                }

                for (index in item.comments.size until holder.commentHolder.size) {
                    holder.commentHolder[index].root.visibility = View.GONE
                }
            }

            if (item.detail != null) {
                layoutDetail.visibility = View.VISIBLE
                layoutDetail.bind(item.detail)
            } else {
                layoutDetail.visibility = View.GONE
            }

            btnReport.setOnClickListener {
                // 신고하기 버튼 눌렀을때의 처리
                val appCompatActivity = AppCompatActivity()
                val fragmentManager = appCompatActivity.supportFragmentManager
                val bottomSheet = ReportBottomSheetDialog()
                bottomSheet.show(fragmentManager, bottomSheet.tag)
            }
        }

        holder.refreshCommentList()
        holder.refreshCommentWrite()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun setListener(listener: ReviewItemListener) {
        this.listener = listener
    }

    interface ReviewItemListener {
        fun comment(position: Int, item: ReviewData, comment: String)
        fun like(position: Int, item: ReviewData, value: Boolean)
    }

    inner class ReviewViewHolder(var binding: ItemDetailReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val commentHolder = ArrayList<ItemReviewCommentBinding>()

        init {
            binding.layoutDetail.setIndicator(true)

            // Comment
            binding.btnComment.setOnClickListener {
                val item = list!![adapterPosition]
                item.visibleComment = !item.visibleComment
                refreshCommentWrite()
            }

            binding.btnCommentCancel.setOnClickListener {
                val item = list!![adapterPosition]
                item.visibleComment = false
                item.cacheComment = ""
                binding.etReviewComment.setText("")
                refreshCommentWrite()
            }

            binding.btnCommentConfirm.setOnClickListener {
                val item = list!![adapterPosition]
                listener?.comment(adapterPosition, item, binding.etReviewComment.text.toString())
            }

            binding.checkboxLike.setOnClickListener {
                val item = list!![adapterPosition]
                listener?.like(adapterPosition, item, (it as CheckBox).isChecked)
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
                item.visibleComment = false
                binding.layoutCommentList.visibility = View.VISIBLE

                refreshCommentWrite()
            } else {
                binding.layoutCommentList.visibility = View.GONE
            }
        }

        fun refreshCommentWrite() {
            val item = list!![adapterPosition]
            if (item.visibleComment) {
                item.visibleCommentList = false
                binding.layoutReviewComment.visibility = View.VISIBLE
                binding.btnComment.setTextColor(Color.WHITE)

                refreshCommentList()
            } else {
                binding.layoutReviewComment.visibility = View.GONE
                binding.btnComment.setTextColor(Color.parseColor("#A5A5A5"))
            }
        }
    }
}