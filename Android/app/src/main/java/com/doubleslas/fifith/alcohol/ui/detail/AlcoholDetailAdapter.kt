package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import kotlinx.android.synthetic.main.item_alcohol_similar.view.*

class AlcoholDetailAdapter(val context: Context) :
    RecyclerView.Adapter<AlcoholDetailAdapter.DetailViewHolder>() {
    var items: MutableList<AlcoholDetailData> = mutableListOf(
        AlcoholDetailData("소주"),
        AlcoholDetailData("잭 다니엘"),
        AlcoholDetailData("카스"),
        AlcoholDetailData("하이트"),
        AlcoholDetailData("참이슬"),
        AlcoholDetailData("청하")

    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(parent)
    }


    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                alcoholName.text = item.alcoholName

            }
        }
    }

    inner class DetailViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_alcohol_similar, parent, false)
    ) {
        val alcoholName = itemView.tv_similar_name
    }
}


