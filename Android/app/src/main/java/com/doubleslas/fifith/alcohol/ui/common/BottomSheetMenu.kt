package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.databinding.ItemMenuListBinding
import com.doubleslas.fifith.alcohol.databinding.RecyclerviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomSheetMenu : BottomSheetDialogFragment() {
    private var binding: RecyclerviewBinding? = null
    private var list: List<String>? = null
    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecyclerviewBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.recyclerview.layoutManager = LinearLayoutManager(context)
            it.recyclerview.adapter = Adapter()
        }
    }

    fun setList(list: List<String>) {
        this.list = list
    }

    fun setOnItemClickListener(listener: ((String) -> Unit)?) {
        onItemClickListener = listener
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.BottomSheetMenuViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.BottomSheetMenuViewHolder {
            val binding =
                ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BottomSheetMenuViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return list?.size ?: 0
        }

        override fun onBindViewHolder(holder: Adapter.BottomSheetMenuViewHolder, position: Int) {
            holder.binding.tv.text = list!![position]
        }

        inner class BottomSheetMenuViewHolder(val binding: ItemMenuListBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(list!![adapterPosition])
                    dismiss()
                }
            }
        }
    }
}