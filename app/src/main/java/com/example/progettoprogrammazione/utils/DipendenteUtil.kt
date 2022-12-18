package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface DipendenteUtil {
    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun createDipendente(context: Context?, eData: Dipendente) {
        firebaseDatabase.getReference("Dipendenti").push().setValue(eData)
    }

    fun getDipendenteData(callBack: FireBaseCallbackDipendente, context: Context?) {
        firebaseDatabase.getReference("Dipendenti")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseDipendente()
                    for(dipend : DataSnapshot in snapshot.children){
                        val dipendente = Dipendente(
                            dipend.child("NomeD").value.toString(),
                            dipend.child("cognomeD").value.toString(),
                            dipend.child("Buisness_emailD").value.toString(),
                            dipend.child("PasswordD").value.toString(),
                            dipend.child("telefonoD").value.toString(),
                            dipend.child("TurnoD").value.toString(),
                            dipend.child("DataAssunsioneD").value.toString(),
                            dipend.child("UriD").value.toString(),
                            dipend.child("LivelloD").value.toString(),
                            dipend.child("StipendioD").value.toString().toInt()
                        )
                        response.dipendenti.add(dipendente)
                    }
                    callBack.onResponse(response)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        context,
                        "Errore durante il caricamento dei dati",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }



}