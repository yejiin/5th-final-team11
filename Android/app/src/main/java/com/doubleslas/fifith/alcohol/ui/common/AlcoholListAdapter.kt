package com.doubleslas.fifith.alcohol.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData

class AlcoholListAdapter : RecyclerView.Adapter<AlcoholListAdapter.AlcoholViewHolder>() {
    private var list: List<AlcoholSimpleData>? = null
    private var onItemClickListener: ((AlcoholSimpleData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlcoholDetailBinding.inflate(inflater, parent, false)

        return AlcoholViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: AlcoholViewHolder, position: Int) {
        val item = list!![position]
        holder.binding.run {
            tvName.text = item.name
            tvType.text = item.category
            tvRating.text = "${item.star}(${item.reviewCnt})"
            rating.rating = item.star
        }
    }

    fun setData(list: List<AlcoholSimpleData>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: ((AlcoholSimpleData) -> Unit)?) {
        onItemClickListener = listener
    }

    inner class AlcoholViewHolder(val binding: ItemAlcoholDetailBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(list!![adapterPosition])
            }
        }
    }
}