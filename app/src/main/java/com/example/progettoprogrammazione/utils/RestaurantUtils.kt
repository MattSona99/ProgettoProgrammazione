package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface RestaurantUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun createRestaurant(context: Context?, rData: Restaurant) {
        firebaseDatabase.getReference("Ristoranti").push().setValue(rData)
        Toast.makeText(context, "Ristorante creato con successo.", Toast.LENGTH_LONG).show()
    }

    fun getRestaurantData(callBack: FireBaseCallbackRestaurant, context: Context?) {
        firebaseDatabase.getReference("Ristoranti")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseRistorante()
                    for (rist: DataSnapshot in snapshot.children) {
                        val ristorante = Restaurant(
                            rist.child("imageR").value.toString(),
                            rist.child("nomeR").value.toString(),
                            rist.child("descrizioneR").value.toString(),
                            rist.child("indirizzoR").value.toString(),
                            rist.child("orariolavorativoR").value.toString(),
                            rist.child("telefonoR").value.toString(),
                            rist.child("tipoCiboR").value.toString(),
                            rist.child("veganR").value.toString().toBoolean(),
                            rist.child("ratingR").value.toString(),
                            rist.child("idR").value.toString(),
                            rist.child("proprietarioR").value.toString()
                        )
                        response.ristoranti.add(ristorante)
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