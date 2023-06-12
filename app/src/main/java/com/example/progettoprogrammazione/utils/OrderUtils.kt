package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackOrder
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa interfaccia implementa determinati metodi per effettuare delle query con il database
// per il recupero, modifica o eliminazione di informazioni relative agli ordini

interface OrderUtils {
    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    // Funzione che crea un nuovo ordine per un ristorantre specifico all'interno del database
    fun createOrder(restID: String, jsonString: JSONArray, context: Context?) {

        firebaseDatabase.getReference("Ristoranti/$restID/Ordini")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val maxid = snapshot.childrenCount.toInt()
                    val data = hashMapOf<String, String>()
                    data["numero"] = "${maxid + 1}"
                    data["json"] = jsonString.toString()
                    data["rID"] = restID
                    data["checked"] = "false"

                    firebaseDatabase.getReference("Ristoranti/$restID/Ordini/${maxid + 1}")
                        .setValue(data)
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

    // Funzionc che recupera tutti gli ordini relativi ad un ristorante specifico nel database
    fun getOrders(dipendente: Dipendente, callback: FireBaseCallbackOrder, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/${dipendente.codiceRistorante}/Ordini")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseOrder()
                    for (order: DataSnapshot in snapshot.children) {
                        val ordine = Order(
                            order.child("numero").value.toString().toInt(),
                            order.child("json").value.toString(),
                            order.child("rID").value.toString(),
                            order.child("checked").value.toString().toBoolean()
                        )
                        response.ordini.add(ordine)
                    }
                    callback.onResponse(response)
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

    // Funzione che elimina gli ordini di un ristorante specifico nel database
    fun deleteOrders(restID: String, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$restID/Ordini").removeValue()
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Gli ordini sono stati eliminati con successo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}