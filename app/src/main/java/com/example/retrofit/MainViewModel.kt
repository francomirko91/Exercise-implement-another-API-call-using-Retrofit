package com.example.retrofit

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {


    private var _dogResult = MutableStateFlow<ApiResponse<MyDataItem>>(ApiResponse.Loading)
    val dogResult : StateFlow<ApiResponse<MyDataItem>>
            get() = _dogResult




    private val retrofit = Retrofit.Builder().baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val dogService = retrofit.create(ApiInterface::class.java)



    fun retrieveNetworkCall (){
        _dogResult.value = ApiResponse.Loading
        viewModelScope.launch {
            try {
                val response = dogService.getData()
                if (response.isSuccessful) {
                    val dogImage = response.body()
                    _dogResult.value = ApiResponse.Success(response.code(),dogImage)
                    Log.e("MainActivity", "Response successful ${response.code()}")

                } else {
                    _dogResult.value = ApiResponse.Error(response.code(),response.message())
                    Log.e("MainActivity", "Response not successful ${response.code()}")
                }

            } catch (e: Exception) {
                _dogResult.value = ApiResponse.Error(500,"Problem")
                Log.e("MainActivity", "Error: ${e.message}")
            }

        }
    }

}
