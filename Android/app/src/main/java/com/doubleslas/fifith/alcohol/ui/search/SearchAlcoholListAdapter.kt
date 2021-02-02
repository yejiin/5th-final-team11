package com.doubleslas.fifith.alcohol.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemSortBinding
import com.doubleslas.fifith.alcohol.enum.SearchSortType
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListAdapter

class SearchAlcoholListAdapter : AlcoholListAdapter() {
    private var sortType: SearchSortType? = null
    private var onSortChangeListener: ((SearchSortType) -> Unit)? = null
    private val sortDialog by lazy {
        SearchSortBottomSheetDialog().apply {
            setOnSortSelectListener {
                sortType = it
                onSortChangeListener?.invoke(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM_TYPE_SORT -> {
                val binding = ItemSortBinding.inflate(inflater, parent, false)
                return SortViewHolder(binding)
            }
        }

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_TYPE_SORT
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        val cnt = super.getItemCount()
        if (cnt == 0) return 0
        return cnt + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SortViewHolder -> holder.binding.run {
                tvSort.text = sortType?.text ?: SearchSortType.Popular.text
            }
            else -> super.onBindViewHolder(holder, position)
        }
    }

    fun setSort(sortType: SearchSortType) {
        this.sortType = sortType
        if (itemCount > 0) notifyItemChanged(0)
    }

    fun setOnSortChangeListener(listener: ((SearchSortType) -> Unit)?) {
        onSortChangeListener = listener
    }

    override fun getItem(position: Int): AlcoholSimpleData {
        return super.getItem(position - 1)
    }

    inner class SortViewHolder(val binding: ItemSortBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val activity = it.context as AppCompatActivity
                sortDialog.setInitSort(sortType ?: SearchSortType.Popular)
                sortDialog.show(activity.supportFragmentManager, null)
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_SORT = 1
    }
}