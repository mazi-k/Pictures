package com.example.pictures.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesAPI {
    @GET("v1/search?q=excited&key=LIVDSRZULELA&limit=20")
    suspend fun getPictures(
        @Query("pos") pos: Int = 0
    ): Response<PicturesResponseData>
}
