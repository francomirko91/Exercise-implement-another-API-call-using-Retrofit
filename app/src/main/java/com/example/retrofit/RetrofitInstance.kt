package com.example.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        val logging = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        }


    }

}