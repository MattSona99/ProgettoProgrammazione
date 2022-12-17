package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface RestaurantUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun createRestaurant(
        callbackRestaurant: FireBaseCallbackRestaurant,
        context: Context?, rData: Restaurant
    ) {

        firebaseDatabase.getReference("Ristoranti").setValue(callbackRestaurant)
            .addOnSuccessListener {
                Toast.makeText(context, "Ristorante Creato con successo", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Errore creazione ristorante", Toast.LENGTH_LONG).show()
            }
    }
}