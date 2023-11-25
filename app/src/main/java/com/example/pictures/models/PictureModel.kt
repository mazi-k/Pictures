package com.example.pictures.models

import android.os.Parcel
import android.os.Parcelable

data class PictureModel(
    val id: String,
    val url: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PictureModel> {
        override fun createFromParcel(parcel: Parcel): PictureModel {
            return PictureModel(parcel)
        }

        override fun newArray(size: Int): Array<PictureModel?> {
            return arrayOfNulls(size)
        }
    }

}