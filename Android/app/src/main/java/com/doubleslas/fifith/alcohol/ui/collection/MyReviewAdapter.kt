package com.doubleslas.fifith.alcohol.ui.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemMyReviewBinding
import com.doubleslas.fifith.alcohol.dto.MyReviewData
import com.doubleslas.fifith.alcohol.ui.common.SortViewHolder

class MyReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<MyReviewData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM_TYPE_SORT -> SortViewHolder.create(inflater, parent)
        }

        val binding = ItemMyReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (list == null) return 0
        return list!!.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SortViewHolder -> holder.bind()
            is ReviewViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ITEM_TYPE_SORT
        }

        return ITEM_TYPE_CONTENT
    }

    private fun getItem(position: Int): MyReviewData {
        return list!![position - 1]
    }


    inner class ReviewViewHolder(private val binding: ItemMyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyReviewData) {
            binding.tvName.text = item.alcoholName
            binding.tvType.text = item.alcoholType
            binding.rating.rating = item.star
            binding.tvRating.text = item.star.toString()
            binding.tvReview.text = item.review
            binding.tvDate.text = item.date
            binding.tvLocation.text = item.location
        }
    }

    companion object {
        private const val ITEM_TYPE_SORT = 0
        private const val ITEM_TYPE_SELECT_ALL = 1
        private const val ITEM_TYPE_CONTENT = 2
    }

}