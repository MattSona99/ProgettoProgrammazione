package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
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
    }

    fun getRestaurantData(callBack: FireBaseCallbackRestaurant, context: Context?) {

        firebaseDatabase.getReference("Ristoranti").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseRistorante()
                    response.ristorante = Restaurant(
                        snapshot.child("imageR").value.toString(),
                        snapshot.child("nomeR").value.toString(),
                        snapshot.child("descrizioneR").value.toString(),
                        snapshot.child("indrizzoR").value.toString(),
                        snapshot.child("orariolavorativoR").value.toString(),
                        snapshot.child("telefonoR").value.toString(),
                        snapshot.child("tipoCiboR").value.toString(),
                        snapshot.child("veganR").value.toString().toBoolean(),
                        snapshot.child("ratingR").value.toString()
                    )
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