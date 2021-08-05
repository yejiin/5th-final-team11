package com.doubleslas.fifith.alcohol.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleslas.fifith.alcohol.databinding.ItemAlcoholSimilarBinding
import com.doubleslas.fifith.alcohol.dto.SimilarAlcoholData

class SimilarAdapter :
    RecyclerView.Adapter<SimilarAdapter.DetailViewHolder>() {

    private var list: List<SimilarAlcoholData>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlcoholSimilarBinding.inflate(inflater, parent, false)
        return DetailViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = list!![position]

        holder.binding.let { b ->
            b.tvName.text = item.name
            Glide.with(b.root)
                .load(item.image)
                .into(b.ivAlcohol)
        }
    }

    fun setData(list: List<SimilarAlcoholData>) {
        this.list = list
    }

    inner class DetailViewHolder(val binding: ItemAlcoholSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val context = binding.root.context
                val item = list!![adapterPosition]
                val intent = AlcoholDetailActivity.getStartIntent(context, item.id)
                context.startActivity(intent)
            }
        }
    }
}


