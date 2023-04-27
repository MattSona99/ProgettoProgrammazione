package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackCart
import com.example.progettoprogrammazione.models.CartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

interface CartUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun getQRData(
        idU: String?,
        callback: FireBaseCallbackCart,
        context: Context?
    ) {
        firebaseDatabase.getReference("Utenti/$idU/Cart")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val gson = Gson()
                    val snapshotJ = gson.toJson(snapshot.value)
                    val responseQR = ResponseCart()
                    responseQR.cart = snapshotJ
                    callback.onResponse(responseQR)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        context,
                        "Errore durante il caricamento dei dati.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    fun addQRData(
        cartItems: ArrayList<CartProduct>,
        idU: String?,
        context: Context?
    ) {
        firebaseDatabase.getReference("Utenti/$idU/Cart")
            .setValue(cartItems).addOnSuccessListener {
                Toast.makeText(context, "QRCode creato con successo.", Toast.LENGTH_LONG)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Errore durante il caricamento dei dati.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
    }

    fun removeQRData(idU: String?, context: Context?) {
        firebaseDatabase.getReference("Utenti/$idU/Cart").removeValue()
        Toast.makeText(context, "QRCode eliminato con successo.", Toast.LENGTH_LONG)
            .show()
    }
}