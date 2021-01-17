package com.doubleslas.fifith.alcohol.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter : RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {
    private var list: List<String>? = null
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchHistoryBinding.inflate(inflater)
        return SearchHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.binding.tvText.text = list!![position]
    }

    fun setList(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, keyword: String)
        fun onDeleteClick(position: Int, keyword: String)
    }

    inner class SearchHistoryViewHolder(val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition, list!![adapterPosition])
            }
            binding.btnDelete.setOnClickListener {
                onItemClickListener?.onDeleteClick(adapterPosition, list!![adapterPosition])
            }
        }
    }
}
