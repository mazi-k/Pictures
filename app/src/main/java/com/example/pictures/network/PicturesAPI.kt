package com.example.pictures.network

import retrofit2.Response
import retrofit2.http.GET

interface PicturesAPI {
    @GET("beers")
    suspend fun getPictures(): Response<List<PicturesResponseData>>
}
