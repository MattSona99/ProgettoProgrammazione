package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe descrive gli attributi e le relative tipologie di un ordine

data class Order (
    var numero: Int? = 0,
    var json: String?,
    var rID: String?,
    var checked: Boolean
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(numero!!)
        parcel.writeString(json)
        parcel.writeString(rID)
        parcel.writeBoolean(checked)
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