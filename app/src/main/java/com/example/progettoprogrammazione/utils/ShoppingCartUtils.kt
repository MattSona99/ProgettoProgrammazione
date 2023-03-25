package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.models.Cart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface ShoppingCartUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun getShoppingCartData(
        idU: String?,
        callBack: FireBaseCallbackShoppingCart,
        context: Context?
    ) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseShoppingCart()
                    for (prod: DataSnapshot in snapshot.children) {
                        val carrello =
                            Cart(
                                prod.child("pName").value.toString(),
                                prod.child("quantity").value.toString().toInt(),
                                prod.child("totPrice").value.toString().toFloat(),
                                prod.child("idProd").value.toString()
                            )

                        response.carrello[prod.child("idP").toString()] = carrello
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

    fun addToShoppingCart(cart: Cart, quantita: Int, idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello/${cart.pName}").setValue(cart)
    }

    fun removeShoppingCart(cart: Cart, idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .child("${cart.pName}").removeValue()

    }

}