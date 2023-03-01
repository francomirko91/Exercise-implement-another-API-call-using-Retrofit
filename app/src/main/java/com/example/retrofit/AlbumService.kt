package com.example.retrofit

import retrofit2.Response
import retrofit2.http.GET


interface AlbumService {

    // Always specify the end-points in the interface

    @GET ("/albums")
    suspend fun getAlbums () : Response<Albums>

}