package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable

class User(
    var Nome: String?,
    var Cognome: String?,
    var Email: String?,
    var Password: String?,
    var Telefono: String?,
    var Uri: String?,
    var Livello: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Nome)
        parcel.writeString(Cognome)
        parcel.writeString(Email)
        parcel.writeString(Password)
        parcel.writeString(Telefono)
        parcel.writeString(Uri)
        parcel.writeString(Livello)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}


