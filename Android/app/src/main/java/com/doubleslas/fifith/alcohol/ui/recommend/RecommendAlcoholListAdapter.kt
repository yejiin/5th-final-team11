package com.doubleslas.fifith.alcohol.ui.recommend

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemSortRecommendBinding
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.sort.SortBottomSheetDialog
import com.doubleslas.fifith.alcohol.sort.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.ui.auth.recommendinfo.RecommendInfoActivity
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListAdapter

class RecommendAlcoholListAdapter : AlcoholListAdapter() {
    private var sortType: RecommendSortType? = null
    private var onSortChangeListener: ((RecommendSortType) -> Unit)? = null
    private val sortDialog by lazy {
        SortBottomSheetDialog(RecommendSortType.values()).apply {
            setOnSortSelectListener {
                sortType = it
                onSortChangeListener?.invoke(it)
            }
        }
    }
    private var rankList: List<AlcoholSimpleData>? = null

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
            else -> {
                super.onBindViewHolder(holder, position)

                val rank = getRank(getItem(position))

                if (holder is AlcoholViewHolder) {
                    holder.binding.let { b ->
                        if (rank == -1) {
                            b.ivAlcohol.background = null
                            b.tvRank.visibility = View.GONE
                        } else {
                            b.ivAlcohol.background =
                                getRankBackground(rank, holder.binding.root.resources)
                            b.tvRank.visibility = View.VISIBLE
                            b.tvRank.setBackgroundResource(
                                when (rank) {
                                    0 -> R.drawable.ic_1st
                                    1 -> R.drawable.ic_2nd
                                    2 -> R.drawable.ic_3rd
                                    else -> R.drawable.ic_etc
                                }
                            )
                            b.tvRank.setTextColor(if (rank < 3) Color.WHITE else Color.BLACK)
                            b.tvRank.text = (rank + 1).toString()
                        }
                    }
                }
            }
        }
    }

    override fun getItem(position: Int): AlcoholSimpleData {
        return super.getItem(position - 1)
    }

    fun setSort(sortType: RecommendSortType) {
        this.sortType = sortType
        if (itemCount > 0) notifyItemChanged(0)
    }

    fun setOnSortChangeListener(listener: ((RecommendSortType) -> Unit)?) {
        onSortChangeListener = listener
    }

    fun setRankList(list: List<AlcoholSimpleData>) {
        rankList = list
    }

    private fun getRank(item: AlcoholSimpleData): Int {
        return rankList?.indexOf(item) ?: -1
    }


    private fun getRankBackground(rank: Int, resources: Resources): Drawable? {
        val id = when (rank) {
            0 -> R.drawable.bg_1st
            1 -> R.drawable.bg_2nd
            2 -> R.drawable.bg_3rd
            else -> null
        } ?: return null

        return ResourcesCompat.getDrawable(resources, id, null)
    }

    inner class SortViewHolder(val binding: ItemSortRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutSort.setOnClickListener {
                val activity = it.context as AppCompatActivity
                sortDialog.show(
                    activity.supportFragmentManager,
                    sortType ?: RecommendSortType.Recommend
                )
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