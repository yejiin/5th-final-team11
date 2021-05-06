package com.doubleslas.fifith.alcohol.ui.common

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemMenuListBinding
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomSheetMenu : BottomSheetDialogFragment() {
    private var binding: RecyclerviewBinding? = null
    private var list: List<String>? = null
    private var onItemClickListener: ((Int, String) -> Unit)? = null
    private var selectIndex: Int? = null

    private val typefaceMedium by lazy {
        ResourcesCompat.getFont(context!!, R.font.noto_sans_medium)
    }
    private val typefaceRegular by lazy {
        ResourcesCompat.getFont(context!!, R.font.noto_sans_regular)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecyclerviewBinding.inflate(inflater, container, false)
        binding!!.root.setBackgroundResource(R.drawable.bg_write_review)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.recyclerview.layoutManager = LinearLayoutManager(context)
            val itemDecoration = DividerItemDecoration(context!!, 1).apply {
                setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
            }
            it.recyclerview.addItemDecoration(itemDecoration)
            it.recyclerview.adapter = Adapter()
        }
    }

    fun setList(list: List<String>) {
        this.list = list
    }

    fun setOnItemClickListener(listener: ((Int, String) -> Unit)?) {
        onItemClickListener = listener
    }

    fun setSelectIndex(position: Int) {
        selectIndex = position
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.BottomSheetMenuViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): Adapter.BottomSheetMenuViewHolder {
            val binding =
                ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BottomSheetMenuViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return list?.size ?: 0
        }

        override fun onBindViewHolder(holder: Adapter.BottomSheetMenuViewHolder, position: Int) {
            holder.binding.tv.text = list!![position]
            if (selectIndex == null || selectIndex != position) {
                holder.binding.ivCheck.visibility = View.INVISIBLE
                holder.binding.tv.typeface = typefaceRegular
                holder.binding.tv.setTextColor(Color.parseColor("#cccccc"))
            } else {
                holder.binding.ivCheck.visibility = View.VISIBLE
                holder.binding.tv.typeface = typefaceMedium
                holder.binding.tv.setTextColor(Color.parseColor("#ffffff"))
            }
        }

        inner class BottomSheetMenuViewHolder(val binding: ItemMenuListBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(adapterPosition, list!![adapterPosition])
                    dismiss()
                }
            }
        }
    }
}