package com.doubleslas.fifith.alcohol.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemSortBinding
import com.doubleslas.fifith.alcohol.enum.SortType

class SortViewHolder(val binding: ItemSortBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var sort: SortType = SortType.Popular
    private var onSortChangeListener: ((SortType) -> Unit)? = null

    private val sortDialog by lazy {
        SortBottomSheetDialog().apply {
            setOnSortSelectListener {
                sort = it
                onSortChangeListener?.invoke(it)
            }
        }
    }

    init {
        binding.root.setOnClickListener {
            val activity = it.context as AppCompatActivity
            sortDialog.show(activity.supportFragmentManager, null)
        }
    }

    fun setOnSortSelectListener(listener: ((SortType) -> Unit)?) {
        onSortChangeListener = listener
    }

    fun bind() {
        binding.tvSort.text = sort.text
    }

    fun setSort(sort: SortType) {
        this.sort = sort
    }

    companion object {
        fun create(inflater: LayoutInflater, parent: ViewGroup): SortViewHolder {
            val binding = ItemSortBinding.inflate(inflater, parent, false)
            return SortViewHolder(binding)
        }
    }
}