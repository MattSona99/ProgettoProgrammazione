package com.example.progettoprogrammazione.models

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

val DIPENDENTE_EXTRA="dipendenteExtra"

class Dipendente(

    var Nome: String?,
    var Cognome: String?,
    var Business_Email: String?,
    var Telefono: String?,
    var Turno:String?,
    var applianceDate:String?,
    var DataAssunzione:String?,
    var Uri: String?,
    var Livello: String?,
    var PartTime:Boolean,
    var Stipendio:Int?,
    var idD: String?,
    var catenaRistorante: String?

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
        parcel.readBoolean(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Nome)
        parcel.writeString(Cognome)
        parcel.writeString(Business_Email)
        parcel.writeString(Telefono)
        parcel.writeString(Turno)
        parcel.writeString(applianceDate)
        parcel.writeString(DataAssunzione)
        parcel.writeString(Uri)
        parcel.writeString(Livello)
        parcel.writeBoolean(PartTime)
        parcel.writeInt(Stipendio!!)
        parcel.writeString(idD)
        parcel.writeString(catenaRistorante)
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



