package com.example.retrofit

import com.google.gson.annotations.SerializedName

//Data class (POJO) this class represent the object

data class AlbumItem(

    @SerializedName ("id")
    val id: Int,

    @SerializedName ("title")
    val title: String,

    @SerializedName("userId")
    val userId: Int
)