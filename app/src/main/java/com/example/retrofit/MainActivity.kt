package com.example.retrofit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.retrofit.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        viewModel.retrieveNetworkCall()

        lifecycleScope.launch {
            viewModel.dogResult.collect { dogResult ->
                when (dogResult) {
                    is ApiResponse.Loading -> Toast.makeText(applicationContext, "LOADING", Toast.LENGTH_SHORT)
                        .show()
                    is ApiResponse.Error -> Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_SHORT).show()
                    is ApiResponse.Success<MyDataItem> -> Picasso.get()
                        .load(dogResult.body?.message).into(binding.imageView)
                }
            binding.imageView.visibility = View.VISIBLE
            }

        }

    }
}


