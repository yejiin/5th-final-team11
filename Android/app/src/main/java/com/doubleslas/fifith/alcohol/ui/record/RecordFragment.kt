package com.doubleslas.fifith.alcohol.ui.record

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentRecordBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.record.cupboard.CupboardFragment
import com.doubleslas.fifith.alcohol.ui.record.myreview.MyReviewFragment
import com.google.android.material.tabs.TabLayoutMediator

class RecordFragment : BaseFragment<FragmentRecordBinding>() {
    private lateinit var adapter: RecordViewPagerAdapter


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecordBinding {
        return FragmentRecordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecordViewPagerAdapter()

        binding?.let { b ->
            setSupportActionBar(b.toolbar)


            b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            b.viewPager.adapter = adapter

            TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.all_cupboard)
                    1 -> getString(R.string.all_my_review)
                    else -> "error"
                }
            }.attach()
        }

        getSupportActionBar()?.apply {
            setDisplayShowTitleEnabled(false)
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.record_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return getCurrentFragment()?.onOptionsItemSelected(item) ?: false
    }

    private fun getCurrentFragment(): Fragment? {
        val index = binding?.viewPager?.currentItem ?: -1
        return childFragmentManager.findFragmentByTag("f${index}")
    }

    private inner class RecordViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) CupboardFragment() else MyReviewFragment()
        }
    }
}