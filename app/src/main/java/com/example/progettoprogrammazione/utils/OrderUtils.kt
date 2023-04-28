package com.example.progettoprogrammazione.utils

import android.content.Context
import android.renderscript.Sampler.Value
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject

interface OrderUtils {
    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase


    fun createOrder(jsonString: String?, restID: String, context: Context?) {

        firebaseDatabase.getReference("Ristoranti/$restID/Ordini")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val maxid = snapshot.childrenCount.toInt()
                    firebaseDatabase.getReference("Ristoranti/$restID/Ordini/${maxid + 1}")
                        .setValue(jsonString)
                    Toast.makeText(context, "Ordine aggiunto con successo.", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        context,
                        "Errore durante il caricamento dell'ordine.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}