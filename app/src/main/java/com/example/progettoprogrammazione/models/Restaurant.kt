package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

var restaurantList = mutableListOf<Restaurant>()

val RESTAURANT_EXTRA="restaurantExtra"

data class Restaurant (
    var image_r: Int,
    var nome_r : String?,
    var descrizioneR: String?,
    val id: Int? = restaurantList.size
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image_r)
        parcel.writeString(nome_r)
        parcel.writeString(descrizioneR)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}
