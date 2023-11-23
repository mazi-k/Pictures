package com.example.pictures.network

import com.google.gson.annotations.SerializedName

data class PicturesResponseData (
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val url: String,
)