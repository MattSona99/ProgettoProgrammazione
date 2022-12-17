package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

var restaurantList = mutableListOf<Restaurant>()

val RESTAURANT_EXTRA="restaurantExtra"

data class Restaurant (

    var imageR: String?,
    var nomeR : String?,
    var descrizioneR: String?,
    var indirizzoR: String?,
    var orariolavorativoR: String?,
    var telefonoR: String?,
    var tipoCiboR: String?,
    var veganR: Boolean?,
    var ratingR: String?,

    val id: Int? = restaurantList.size
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageR)
        parcel.writeString(nomeR)
        parcel.writeString(descrizioneR)
        parcel.writeString(indirizzoR)
        parcel.writeString(orariolavorativoR)
        parcel.writeString(telefonoR)
        parcel.writeString(tipoCiboR)
        parcel.writeBoolean(veganR!!)
        parcel.writeString(ratingR)
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
