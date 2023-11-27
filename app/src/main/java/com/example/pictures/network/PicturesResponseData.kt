package com.example.pictures.network

import com.google.gson.annotations.SerializedName

data class PicturesResponseData(
    @SerializedName("results")
    val data: ArrayList<Results>,
    @SerializedName("next")
    var next: String? = null
)

data class Results(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("media")
    var media: ArrayList<Media> = arrayListOf(),
)

data class Media(
    @SerializedName("gif") var gif: Gif? = Gif(),
)

data class Gif(
    @SerializedName("preview")
    var preview: String? = null,
    @SerializedName("url")
    var url: String? = null,
)
