package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

class Cart(
    var pName: String?,
    var quantity: Int? = 0,
    var totPrice: Float? = 0f,
    var pID: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pName)
        parcel.writeInt(quantity!!)
        parcel.writeFloat(totPrice!!)
        parcel.writeString(pID)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cart> {
        override fun createFromParcel(parcel: Parcel): Cart {
            return Cart(parcel)
        }

        override fun newArray(size: Int): Array<Cart?> {
            return arrayOfNulls(size)
        }
    }

}