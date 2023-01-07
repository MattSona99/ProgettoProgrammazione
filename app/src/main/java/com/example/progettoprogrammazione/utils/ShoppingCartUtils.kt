package com.example.progettoprogrammazione.utils

import android.content.Context
import android.os.Parcel
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

interface ShoppingCartUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun getShopingCartData(idU: String?, callBack: FireBaseCallbackShoppingCart, context: Context?){
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseShoppingCart()
                    for (prod: DataSnapshot in snapshot.children) {
                        val carrello=
                            Cart(
                                prod.child("pName").value.toString() ,
                                prod.child("quantity").value as Int?,
                                prod.child("totPrice").value as Float?,
                                prod.child("idProd").value.toString(),
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

    fun addToShoppingCart( cart: Cart, quantita:Int,idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello/${cart.pName}").setValue(cart)
    }

    fun addquantityShoppingCart( cart: Cart, quantita:Int,idU: String?) {
        //SALVO VALORE QUANTITA
        var pezzi=quantita
        pezzi++
    }

    fun removequantityShoppingCart( cart: Cart, quantita:Int,idU: String?) {

        var pezzi = quantita
        if (pezzi <= 0) {
            removeShoppingCart(cart, idU)
        }else{
            pezzi--
        }

    }


    fun removeShoppingCart(cart: Cart,idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .child("${cart.pName}").removeValue()

    }


    /*
    fun modifyShoppingCart(context: Context?, pData: Product) {
        firebaseDatabase.getReference("Utenti").child("Carrello").setValue(pData)
    }
    */
}