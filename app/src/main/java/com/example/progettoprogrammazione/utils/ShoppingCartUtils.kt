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
                            Cart( prod.child("pName").value.toString() ,
                                prod.child("quantity").value as Int?,
                                prod.child("totPrice").value as Float?,
                                prod.child("quantit").value.toString(),

                                )
                            /*
                            Product(
                            prod.child("descrizioneP").value.toString(),
                            prod.child("prezzoP").value.toString(),
                            prod.child("nomeP").value.toString(),
                            prod.child("idP").value.toString()
                            )
                            */
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

    fun addShoppingCart( pData: Product, quantita:Int,idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello/${pData.nomeP}").setValue(pData)

    }

    fun removeShoppingCart(pData: Product,idU: String?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .child("${pData.nomeP}").removeValue()

    }

    /*
    fun modifyShoppingCart(context: Context?, pData: Product) {
        firebaseDatabase.getReference("Utenti").child("Carrello").setValue(pData)
    }
    */
}