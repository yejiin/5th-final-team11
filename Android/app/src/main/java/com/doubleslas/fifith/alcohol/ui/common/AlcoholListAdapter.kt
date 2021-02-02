package com.doubleslas.fifith.alcohol.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleslas.fifith.alcohol.databinding.ItemAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity

open class AlcoholListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<AlcoholSimpleData>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlcoholDetailBinding.inflate(inflater, parent, false)
        return AlcoholViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE_ALCOHOL
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlcoholViewHolder -> holder.binding.run {
                val item = getItem(position)
                Glide.with(ivAlcohol.context)
                    .load(item.thumbnail)
                    .into(ivAlcohol)
                tvName.text = item.name
                tvType.text = item.category
                tvRating.text = item.star.toString()
                tvReviewCnt.text = "(${item.reviewCnt})"
                rating.rating = item.star
                divider.visibility = if (position != itemCount - 1) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    fun setData(list: List<AlcoholSimpleData>) {
        this.list = list
        notifyDataSetChanged()
    }

    protected open fun getItem(position: Int): AlcoholSimpleData {
        return list!![position]
    }

    inner class AlcoholViewHolder(val binding: ItemAlcoholDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val context = it.context
                val item = getItem(adapterPosition)
                context.startActivity(AlcoholDetailActivity.getStartIntent(context, item.aid))
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_ALCOHOL = 0
    }
}