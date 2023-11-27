package com.example.pictures.models

object Repository {

    private var cache: MutableList<PictureModel> = mutableListOf()
    private var next: Int = 0

    fun getCachePictures() = cache

    fun addCachePictures(list: MutableList<PictureModel>) {
        for (picture in list) {
            cache.add(picture)
        }
    }

    fun setNext(newNextPos: Int) {
        next = newNextPos
    }

    fun getNext() = next
}