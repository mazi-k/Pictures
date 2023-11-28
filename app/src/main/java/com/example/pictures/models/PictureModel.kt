package com.example.pictures.models

import android.os.Parcel
import android.os.Parcelable

data class PictureModel(
    val id: String,
    val urlGif: String,
    val urlPic: String
)