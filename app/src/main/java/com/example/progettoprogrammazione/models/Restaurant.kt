package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(

    var imageR: String?,
    var nomeR: String?,
    var descrizioneR: String?,
    var indirizzoR: String?,
    var orarioinizioR: String?,
    var orariofineR: String?,
    var telefonoR: String?,
    var tipoCiboR: String?,
    var veganR: Boolean,
    var ratingR: Double,
    var nRatings: Int,
    var idR: String?,
    var proprietarioR: String?,
//    var menus: List<Menu?>?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
//        parcel.createTypedArrayList(Menu)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageR)
        parcel.writeString(nomeR)
        parcel.writeString(descrizioneR)
        parcel.writeString(indirizzoR)
        parcel.writeString(orarioinizioR)
        parcel.writeString(orariofineR)
        parcel.writeString(telefonoR)
        parcel.writeString(tipoCiboR)
        parcel.writeByte(if (veganR) 1 else 0)
        parcel.writeDouble(ratingR)
        parcel.writeInt(nRatings)
        parcel.writeString(idR)
        parcel.writeString(proprietarioR)
//        parcel.writeTypedList(menus)
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

data class Menu(
        val name: String?,
        val price: Float,
        val uri: String?,
        var totInCart: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeFloat(price)
            parcel.writeString(uri)
            parcel.writeInt(totInCart)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Menu> {
            override fun createFromParcel(parcel: Parcel): Menu {
                return Menu(parcel)
            }

            override fun newArray(size: Int): Array<Menu?> {
                return arrayOfNulls(size)
            }
        }
    }

