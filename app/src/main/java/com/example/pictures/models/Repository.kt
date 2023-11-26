package com.example.pictures.models

object Repository {

    private var cache: MutableList<PictureModel> = mutableListOf()

    fun getCachePictures() = cache

    fun putCachePictures(list: MutableList<PictureModel>) {
        cache = list
    }
}