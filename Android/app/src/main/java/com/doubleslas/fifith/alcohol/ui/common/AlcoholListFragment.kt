package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.FragmentAlcoholListBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.viewmodel.AlcoholListViewModel

class AlcoholListFragment : Fragment() {
    private val category by lazy { arguments?.getString(ARGUMENT_CATEGORY) ?: "전체" }
    private var binding: FragmentAlcoholListBinding? = null
    private val listViewModel by lazy {
        ViewModelProvider(this, AlcoholListViewModel.Factory(category))
            .get(AlcoholListViewModel::class.java)
    }
    private val adapter by lazy { AlcoholListAdapter() }

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
            it.recyclerview.adapter = adapter
        }

        listViewModel.loadList()

        listViewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                }
            }
        })
    }

    companion object {
        const val ARGUMENT_CATEGORY = "ARGUMENT_CATEGORY"
    }
}