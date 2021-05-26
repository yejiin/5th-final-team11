package com.doubleslas.fifith.alcohol.ui.record.myreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemMyReviewBinding
import com.doubleslas.fifith.alcohol.databinding.ItemSortBinding
import com.doubleslas.fifith.alcohol.dto.MyReviewData
import com.doubleslas.fifith.alcohol.sort.SortBottomSheetDialog
import com.doubleslas.fifith.alcohol.sort.enum.MyReviewSortType
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity

class MyReviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<MyReviewData>? = null
    private var sortType: MyReviewSortType? = null
    private var isSelectMode = false

    private var onSortChangeListener: ((MyReviewSortType) -> Unit)? = null
    private val sortDialog by lazy {
        SortBottomSheetDialog(MyReviewSortType.values()).apply {
            setOnSortSelectListener {
                sortType = it
                onSortChangeListener?.invoke(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_SORT -> {
                val binding = ItemSortBinding.inflate(inflater, parent, false)
                SortViewHolder(binding)
            }
            else -> {
                val binding = ItemMyReviewBinding.inflate(inflater, parent, false)
                ReviewViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        if (list == null) return 0
        if (isSelectMode) return list!!.size
        return list!!.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SortViewHolder -> holder.binding.run {
                tvSort.text = sortType?.text ?: MyReviewSortType.Time.text
            }
            is ReviewViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (!isSelectMode && position == 0) {
            return ITEM_TYPE_SORT
        }

        return ITEM_TYPE_CONTENT
    }

    private fun getItem(position: Int): MyReviewData {
        if (isSelectMode) {
            return list!![position]
        }
        return list!![position - 1]
    }

    fun setData(list: List<MyReviewData>) {
        this.list = list
    }

    fun setOnChangeSortListener(listener: ((MyReviewSortType) -> Unit)?) {
        this.onSortChangeListener = listener
    }

    fun setSelectMode(value: Boolean) {
        isSelectMode = value

        if (list != null) {
            for (item in list!!) {
                item.isSelect = false
            }
        }
    }

    inner class ReviewViewHolder(private val binding: ItemMyReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (!isSelectMode) {
                    val intent = AlcoholDetailActivity.getStartIntent(
                        it.context,
                        getItem(adapterPosition).aid
                    )
                    it.context.startActivity(intent)
                } else {
                    binding.checkbox.isChecked = !binding.checkbox.isChecked

                }
            }
            binding.layoutBottom.setOnClickListener {
                val item = getItem(adapterPosition)
                item.visibleReview = !item.visibleReview
                setVisibleDetail(item.visibleReview)
            }
            binding.checkbox.setOnCheckedChangeListener { view, b ->
                binding.root.setBackgroundResource(
                    if (b) R.drawable.bg_item_my_review_select else R.drawable.bg_item_my_review
                )
                getItem(adapterPosition).isSelect = b
            }
            binding.layoutDetail.setIndicator(true)
        }

        fun bind() {
            val item = getItem(adapterPosition)
            Glide.with(binding.ivAlcohol)
                .load(item.thumbnail)
                .into(binding.ivAlcohol)
            binding.tvName.text = item.alcoholName
//            binding.tvType.text = item.alcoholType
            binding.rating.rating = item.star
            binding.tvRating.text = item.star.toString()
            binding.tvReview.text = item.content

            if (isSelectMode) {
                binding.checkbox.isChecked = item.isSelect
                binding.checkbox.isVisible = true
            } else {
                binding.checkbox.isChecked = false
                binding.checkbox.isVisible = false
            }


            val detail = item.detail
            if (detail == null) {
                binding.layoutDetail.visibility = View.GONE
            } else {
                binding.layoutDetail.visibility = View.VISIBLE
                binding.layoutDetail.bind(detail)
            }

            setVisibleDetail(item.visibleReview)
        }


        private fun setVisibleDetail(visible: Boolean) {
            binding.layoutReview.isVisible = visible
            if (visible) {
                binding.ivBottom.rotation = 180f
            } else {
                binding.ivBottom.rotation = 0f
            }
        }
    }


    inner class SortViewHolder(val binding: ItemSortBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val activity = it.context as AppCompatActivity
                sortDialog.show(
                    activity.supportFragmentManager,
                    sortType ?: MyReviewSortType.Time
                )
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_SORT = 0
        private const val ITEM_TYPE_SELECT_ALL = 1
        private const val ITEM_TYPE_CONTENT = 2
    }

}