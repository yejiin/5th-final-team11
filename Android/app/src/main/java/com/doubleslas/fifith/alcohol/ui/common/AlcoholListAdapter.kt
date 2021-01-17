package com.doubleslas.fifith.alcohol.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.databinding.ItemSortBinding
import com.doubleslas.fifith.alcohol.enum.SortType
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity

class AlcoholListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<AlcoholSimpleData>? = null
    private var onSortChangeListener: ((SortType) -> Unit)? = null
    private lateinit var sortViewHolder: SortViewHolder


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM_TYPE_SORT -> {
                sortViewHolder = SortViewHolder.create(inflater, parent).apply {
                    setOnSortSelectListener {
                        onSortChangeListener?.invoke(it)
                    }
                }
                return sortViewHolder
            }
        }

        val binding = ItemAlcoholDetailBinding.inflate(inflater, parent, false)
        return AlcoholViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_TYPE_SORT
        return ITEM_TYPE_ALCOHOL
    }

    override fun getItemCount(): Int {
        if (list == null || list!!.isEmpty()) return 0
        return list!!.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SortViewHolder -> holder.bind()
            is AlcoholViewHolder -> holder.binding.run {
                val item = getItem(position)
                tvName.text = item.name
                tvType.text = item.category
                tvRating.text = item.star.toString()
                tvReviewCnt.text = "(${item.reviewCnt})"
                rating.rating = item.star
            }
        }
    }

    fun setSort(sortType: SortType) {
        sortViewHolder.setSort(sortType)
        if (itemCount > 0) notifyItemChanged(0)
    }

    fun setData(list: List<AlcoholSimpleData>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnSortChangeListener(listener: ((SortType) -> Unit)?) {
        onSortChangeListener = listener
    }

    private fun getItem(position: Int): AlcoholSimpleData {
        return list!![position - 1]
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
        private const val ITEM_TYPE_SORT = 0
        private const val ITEM_TYPE_ALCOHOL = 1
    }
}