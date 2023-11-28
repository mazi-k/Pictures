package com.example.pictures.models

object Repository {

    private val cache: MutableList<PictureModel> = mutableListOf()
    private var next: Int = 0
    private const val LIMIT_ON_PAGE = 20

    fun getCachePictures() = cache

    fun addCachePictures(list: MutableList<PictureModel>) {
        for (picture in list) {
            cache.add(picture)
        }
    }

    fun setNext() {
        next += LIMIT_ON_PAGE
    }

    fun getNext() = next
}