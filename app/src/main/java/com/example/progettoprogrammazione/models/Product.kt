package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

data class Product(
//    var imageProduct: String?,
    var nomeProduct: String?,
    var prezzoProduct: String?,
    var descrizioneProduct: String?,
    var veganProduct: Boolean,
    var idProduct: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
 //       parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(imageProduct)
        parcel.writeString(nomeProduct)
        parcel.writeString(prezzoProduct)
        parcel.writeString(descrizioneProduct)
        parcel.writeByte(if (veganProduct) 1 else 0)
        parcel.writeString(idProduct)
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