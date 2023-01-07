package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface ShoppingCartUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun getShopingCartData(idU: String?, callBack: FireBaseCallbackShoppingCart, context: Context?){
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseShoppingCart()
                    for (prod: DataSnapshot in snapshot.children) {
                        val carrello= Product(
                            prod.child("descrizioneP").value.toString(),
                            prod.child("prezzoP").value.toString(),
                            prod.child("nomeP").value.toString(),
                            prod.child("idP").value.toString()
                        )
                        response.carrello.set(prod.child("idP").toString(),carrello)
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

    fun addShoppingCart(context: Context?, pData: Product, quantita:Int,idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello").setValue(pData)
    }

    fun removeShoppingCart(context: Context?, pData: Product,idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello").child("${pData}").removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    context,
                    "Prodotto rimosso con successo.",
                    Toast.LENGTH_LONG
                ).show()
            } else Toast.makeText(
                context,
                "Errore durante l'eliminazione del prodotto",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    /*
    fun modifyShoppingCart(context: Context?, pData: Product) {
        firebaseDatabase.getReference("Utenti").child("Carrello").setValue(pData)
    }
    */
}