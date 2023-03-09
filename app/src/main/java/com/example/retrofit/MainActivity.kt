package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var dogService: ApiInterface
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder().baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        dogService = retrofit.create(ApiInterface::class.java)


        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = dogService.getData()
                if (response.isSuccessful) {
                    val dogImage = response.body()
                    // Visualizza l'immagine casuale del cane nella textview "dog"
                    binding.textView.text = dogImage?.message
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


