package com.doubleslas.fifith.alcohol.ui.record.cupboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleslas.fifith.alcohol.databinding.ItemCupboardBinding
import com.doubleslas.fifith.alcohol.dto.CupboardData
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity

class CupboardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<CupboardData>? = null
    private var selectMode = false
    private val selectedItems = ArrayList<CupboardData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCupboardBinding.inflate(inflater, parent, false)
        return CupboardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is CupboardViewHolder) return
        val item = list!![position]
        holder.binding.let { b ->
            Glide.with(b.ivAlcohol)
                .load(item.image)
                .into(b.ivAlcohol)
        }
    }

    fun setData(list: List<CupboardData>?) {
        this.list = list
    }

    fun setSelectMode(value: Boolean) {
        selectMode = true
        selectedItems.clear()
    }

    inner class CupboardViewHolder(val binding: ItemCupboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val context = binding.root.context
                val item = list!![adapterPosition]
                val intent = AlcoholDetailActivity.getStartIntent(context, item.aid)
                context.startActivity(intent)
            }
        }
    }
}