package com.doubleslas.fifith.alcohol.ui.recommend

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemSortBinding
import com.doubleslas.fifith.alcohol.databinding.ItemSortRecommendBinding
import com.doubleslas.fifith.alcohol.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.ui.auth.recommendinfo.RecommendInfoActivity
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListAdapter

class RecommendAlcoholListAdapter : AlcoholListAdapter() {
    private var sortType: RecommendSortType? = null
    private var onSortChangeListener: ((RecommendSortType) -> Unit)? = null
    private val sortDialog by lazy {
        RecommendSortBottomSheetDialog().apply {
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
                val binding = ItemSortRecommendBinding.inflate(inflater, parent, false)
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
                tvSort.text = sortType?.text ?: RecommendSortType.Recommend.text
            }
            else -> super.onBindViewHolder(holder, position)
        }
    }

    fun setSort(sortType: RecommendSortType) {
        this.sortType = sortType
        if (itemCount > 0) notifyItemChanged(0)
    }

    fun setOnSortChangeListener(listener: ((RecommendSortType) -> Unit)?) {
        onSortChangeListener = listener
    }

    override fun getItem(position: Int): AlcoholSimpleData {
        return super.getItem(position - 1)
    }

    inner class SortViewHolder(val binding: ItemSortRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutSort.setOnClickListener {
                val activity = it.context as AppCompatActivity
                sortDialog.setInitSort(sortType ?: RecommendSortType.Recommend)
                sortDialog.show(activity.supportFragmentManager, null)
            }

            binding.layoutRecommend.setOnClickListener {
                val intent = Intent(it.context, RecommendInfoActivity::class.java)
                it.context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_SORT = 1
    }
}