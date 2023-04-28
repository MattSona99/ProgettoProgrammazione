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
        firebaseDatabase.getReference("Dipendenti").push().setValue(eData)
    }

    fun getDipendenteData(
        emailDip: String,
        callBack: FireBaseCallbackDipendente,
        context: Context?
    ) {
        firebaseDatabase.getReference("Dipendenti")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseDipendente()
                    for (dipend: DataSnapshot in snapshot.children) {
                        if (emailDip == dipend.child("emailDipendente").value.toString()) {
                            val dipendente = Dipendente(
                                dipend.child("nome").value.toString(),
                                dipend.child("cognome").value.toString(),
                                dipend.child("telefono").value.toString(),
                                dipend.child("turno").value.toString(),
                                dipend.child("dataAssunzione").value.toString(),
                                dipend.child("livello").value.toString(),
                                dipend.child("partTime").value.toString().toBoolean(),
                                dipend.child("stipendio").value.toString(),
                                dipend.child("idDipendente").value.toString(),
                                dipend.child("emailDipendente").value.toString(),
                                dipend.child("codiceRistorante").value.toString()
                            )
                            response.dipendenti.add(dipendente)
                        }
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

