package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

class CartProduct(
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

    companion object CREATOR : Parcelable.Creator<CartProduct> {
        override fun createFromParcel(parcel: Parcel): CartProduct {
            return CartProduct(parcel)
        }

        override fun newArray(size: Int): Array<CartProduct?> {
            return arrayOfNulls(size)
        }
    }

}