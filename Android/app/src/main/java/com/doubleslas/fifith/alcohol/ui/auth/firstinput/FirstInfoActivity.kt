package com.doubleslas.fifith.alcohol.ui.auth.firstinput

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityFirstInfoBinding
import com.doubleslas.fifith.alcohol.viewmodel.FirstInfoViewModel

class FirstInfoActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(FirstInfoViewModel::class.java) }


    private val drinkCheckObserver = Observer<Boolean> {
        val checked =
            (viewModel.checkBeer.value ?: false)
                    || (viewModel.checkLiquor.value ?: false)
                    || (viewModel.checkWine.value ?: false)

        binding.btnNext.isEnabled = checked
    }

    private lateinit var binding: ActivityFirstInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, DrinkTypeSelectFragment())
            .commit()

        viewModel.checkLiquor.observe(this, drinkCheckObserver)
        viewModel.checkWine.observe(this, drinkCheckObserver)
        viewModel.checkBeer.observe(this, drinkCheckObserver)
    }


}