package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

var dipendenteList = mutableListOf<Dipendente>()

val DIPENDENTE_EXTRA="dipendenteExtra"

class Dipendente(

    var Nome: String?,
    var Cognome: String?,
    var Business_Email: String?,
    var Password: String?,
    var Telefono: String?,
    var Turno:String?,
    var DataAssunzione:String?,
    var Uri: String?,
    var Livello: String?,
    var PartTime:String?,
    var Stipendio:Int?,

    val id: Int? = dipendenteList.size
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
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Nome)
        parcel.writeString(Cognome)
        parcel.writeString(Business_Email)
        parcel.writeString(Password)
        parcel.writeString(Telefono)
        parcel.writeString(Turno)
        parcel.writeString(DataAssunzione)
        parcel.writeString(Uri)
        parcel.writeString(Livello)
        parcel.writeString(PartTime)
        parcel.writeInt(Stipendio!!)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dipendente> {
        override fun createFromParcel(parcel: Parcel): Dipendente {
            return Dipendente(parcel)
        }

        override fun newArray(size: Int): Array<Dipendente?> {
            return arrayOfNulls(size)
        }
    }
}



