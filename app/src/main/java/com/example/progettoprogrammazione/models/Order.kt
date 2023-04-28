package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

data class Order (
    var n: Int? = 0,
    var json: String?,
    var rid: String?,
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(n!!)
        parcel.writeString(json)
        parcel.writeString(rid)
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }


}