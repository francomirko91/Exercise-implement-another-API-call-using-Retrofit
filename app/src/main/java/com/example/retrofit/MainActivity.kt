package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofit.RetrofitInstance.Companion.logging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView : TextView = findViewById(R.id.textView)

        logging.level = HttpLoggingInterceptor.Level.BODY



        val retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        val responsiveLiveData : LiveData<Response<Albums>> =
            liveData {
                val response = retrofitService.getAlbums()
                emit(response)
            }

        responsiveLiveData.observe(this, Observer {
            val albumLists = it.body()?.listIterator()

            if (albumLists != null){
                while (albumLists.hasNext()){
                    val albumItem = albumLists.next()
                    //Log.i("TAGY",albumItem.title)

                    val result = "Album Title: ${albumItem.title}\n"

                    textView.append(result)

                }
            }
        })

    }
}