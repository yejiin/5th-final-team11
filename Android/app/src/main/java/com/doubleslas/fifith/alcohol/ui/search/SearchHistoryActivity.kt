package com.doubleslas.fifith.alcohol.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.ActivitySearchHistoryBinding
import com.doubleslas.fifith.alcohol.viewmodel.SearchViewModel

class SearchHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchHistoryBinding
    private val adapter by lazy { SearchHistoryAdapter() }
    private val vm by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
// TODO :
//            it.setHomeAsUpIndicator()

            it.setDisplayShowTitleEnabled(false)
        }

        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        binding.btnSearch.setOnClickListener {
            search()
        }

        binding.rvSearchHistory.layoutManager = LinearLayoutManager(this)
        binding.rvSearchHistory.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        adapter.setList(vm.getHistoryList())
        binding.etSearch.requestFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun search() {
        val keyword = binding.etSearch.text.toString()

        if (keyword.isEmpty()){
            return
        }

        vm.search(keyword)
        Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchHistoryActivity::class.java)
        }
    }
}