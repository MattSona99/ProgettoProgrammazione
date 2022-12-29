package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

data class Product(
    var nomeP: String?,
    var prezzoP: String?,
    var descrizioneP: String?,
    var idP: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nomeP)
        parcel.writeString(prezzoP)
        parcel.writeString(descrizioneP)
        parcel.writeString(idP)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}