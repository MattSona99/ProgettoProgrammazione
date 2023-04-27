package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackDipendente
import com.example.progettoprogrammazione.models.Dipendente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface DipendenteUtils {
    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun createDipendente(context: Context?, eData: Dipendente) {
        firebaseDatabase.getReference("Dipendenti/${eData.codiceRistorante}").push().setValue(eData)
    }

    fun getDipendenteData(callBack: FireBaseCallbackDipendente, context: Context?) {
        firebaseDatabase.getReference("Dipendenti")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseDipendente()
                    for (dipend: DataSnapshot in snapshot.children) {
                        val dipendente = Dipendente(
                            dipend.child("Nome").value.toString(),
                            dipend.child("Cognome").value.toString(),
                            dipend.child("Telefono").value.toString(),
                            dipend.child("Turno").value.toString(),
                            dipend.child("DataAssunzione").value.toString(),
                            dipend.child("Livello").value.toString(),
                            dipend.child("PartTime").value.toString().toBoolean(),
                            dipend.child("Stipendio").value.toString(),
                            dipend.child ("idDipendente").value.toString(),
                            dipend.child("codiceRistorante").value.toString(),
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

