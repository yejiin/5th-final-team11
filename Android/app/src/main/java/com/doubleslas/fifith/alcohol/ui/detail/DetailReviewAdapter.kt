package com.doubleslas.fifith.alcohol.ui.detail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemDetailReviewBinding
import com.doubleslas.fifith.alcohol.model.network.dto.ReviewData
import com.doubleslas.fifith.alcohol.ui.reivew.ReportBottomSheetDialog
import kotlinx.android.synthetic.main.item_detail_review.view.*

class DetailReviewAdapter :
    RecyclerView.Adapter<DetailReviewAdapter.ReviewViewHolder>() {

    private var list: List<ReviewData>? = null

    inner class ReviewViewHolder(var binding: ItemDetailReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var nickname = binding.tvDetailNickname
        var content = binding.tvDetailReview
        var rating = binding.reviewRating
        var love = binding.tvLikeCount
        var loveClcik = false
        var hangover = binding.layoutDetailReview.seekBarHangover.seekBar
        var place = binding.layoutDetailReview.etPlace
        var price = binding.layoutDetailReview.etPrice
        var date = binding.layoutDetailReview.etCalendar
        var drink = binding.layoutDetailReview.etDrink
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemDetailReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReviewViewHolder(binding)
    }

    fun setData(list: List<ReviewData>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        list!![position].let { item ->
            with(holder) {
                nickname.text = item.nickname
                content.text = item.content
                rating.rating = item.star
                love.text = item.love.toString()

                if (item.detail != null) {
                    hangover.progress = item.detail.hangover!!
                    place.setText(item.detail.place)
                    price.setText(item.detail.price.toString())
                    date.setText(item.detail.date)
                    drink.setText(item.detail.drink.toString())
                } else {
                    binding.layoutDetailReview.layoutDetailReview.visibility = View.GONE
                }


                binding.layoutDetailReview.etCalendar.isEnabled = false
                binding.layoutDetailReview.etDrink.isEnabled = false
                binding.layoutDetailReview.etPlace.isEnabled = false
                binding.layoutDetailReview.etPrice.isEnabled = false
                binding.layoutDetailReview.seekBarHangover.seekBar.isEnabled = false
                binding.layoutDetailReview.seekBarHangover.tvLabel1.text = "없음"
                binding.layoutDetailReview.seekBarHangover.tvLabel2.text = "심함"

                binding.btnComment.setOnClickListener {
                    // 댓글 달기 버튼
                    binding.layoutReviewComment.visibility = View.VISIBLE
                    binding.btnComment.setTextColor(Color.parseColor("#FFFFFF"))
                }

                binding.btnCommentConfirm.setOnClickListener {
                    // 리뷰 댓글 등록
                }

                binding.btnCommentCancel.setOnClickListener {
                    // 리뷰 댓글 등록 취소
                    binding.layoutReviewComment.visibility = View.GONE
                    binding.btnComment.setTextColor(Color.parseColor("#A5A5A5"))
                }

                binding.btnReport.setOnClickListener {
                    val appCompatActivity = AppCompatActivity()
                    val fragmentManager = appCompatActivity.supportFragmentManager

                    val bottomSheet = ReportBottomSheetDialog()
                    bottomSheet.show(fragmentManager, bottomSheet.tag)
                }

                var likeClicked = false
                binding.btnLike.setOnClickListener {
                    // 리뷰 좋아요 버튼 눌렀을 때의 처리
                    likeClicked = !likeClicked
                    if (!likeClicked) {
                        binding.ivReviewLike.setImageResource(R.drawable.ic_heart_gray)
                    } else {
                        binding.ivReviewLike.setImageResource(R.drawable.ic_heart_orange)
                    }
                }


            }

        }


    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }




}