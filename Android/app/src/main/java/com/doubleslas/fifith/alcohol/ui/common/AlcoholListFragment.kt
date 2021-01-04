package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentAlcoholListBinding

class AlcoholListFragment : Fragment() {
    private var binding: FragmentAlcoholListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlcoholListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.recyclerview.layoutManager = LinearLayoutManager(context)
            it.recyclerview.adapter = AlcoholListAdapter()

            it.tvSort.text = getString(R.string.sort_popular)
            it.tvSort.setOnClickListener {
                val b = BottomSheetMenu().apply {
                    setList(
                        listOf(
                            getString(R.string.sort_popular),
                            getString(R.string.sort_review),
                            getString(R.string.sort_favorite),
                            getString(R.string.sort_abv),
                            getString(R.string.sort_price_asc),
                            getString(R.string.sort_price_desc)
                        )
                    )
                }
                b.show(fragmentManager!!, null)
            }
        }
    }
}