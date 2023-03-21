package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val retrofit = Retrofit.Builder().baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val dogService = retrofit.create(ApiInterface::class.java)



        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = dogService.getData()
                if (response.isSuccessful) {
                    val dogImage = response.body()
                    runOnUiThread {

                        Picasso.get().load(dogImage?.message).into(binding.imageView);
                    }

                    Log.e("MainActivity", "Response successful ${response.code()}")

                } else {
                    Log.e("MainActivity", "Response not successful ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}")
            }

        }

    }

}


