package com.doubleslas.fifith.alcohol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.RetroLiveData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val liveData = RetroLiveData<String>()

        liveData.observe(this, Observer {
            when(it){
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {

                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

}