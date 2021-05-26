package com.doubleslas.fifith.alcohol.ui.common.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.common.ToolbarMenuBottomSheetDialog
import com.doubleslas.fifith.alcohol.ui.licence.LicenceActivity

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    protected var binding: B? = null
        private set


    protected val toolbarMenu by lazy{
        ToolbarMenuBottomSheetDialog().apply {
            setOnItemClickListener { _, value ->
                when (value) {
                    getString(R.string.all_licence) -> {
                        val intent = Intent(context, LicenceActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = createViewBinding(inflater, container)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): B


    fun setSupportActionBar(toolbar: Toolbar) {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
    }

    fun getSupportActionBar(): ActionBar? {
        return (activity as? AppCompatActivity)?.supportActionBar
    }
}