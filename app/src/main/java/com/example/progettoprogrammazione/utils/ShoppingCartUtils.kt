package com.example.progettoprogrammazione.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.models.Product
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
                                prod.child("pname").value.toString(),
                                prod.child("quantity").value.toString().toInt(),
                                prod.child("totPrice").value.toString().toFloat(),
                                prod.child("pid").value.toString()
                            )

                        response.carrello.add(carrello)
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

    fun addToShoppingCart(cart: Cart, quantita: Int, idU: String?, context: Context?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello/${cart.pName}").setValue(cart)
        Toast.makeText(context, "Prodotto aggiunto con successo al carrello.", Toast.LENGTH_LONG).show()
    }

    fun removeShoppingCart(cart: Cart, idU: String?, context: Context?) {
        firebaseDatabase.getReference("Utenti/$idU/Carrello")
            .child("${cart.pName}").removeValue()
        Toast.makeText(context, "Prodotto rimosso con successo dal carrello.", Toast.LENGTH_LONG).show()
    }

    fun modifyProdC(cart: Cart, idU: String?, context: Context?) {

        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.fragment_modify_quantity_cart, null)
        val addDialog = AlertDialog.Builder(context)

        val quantityC = v.findViewById<EditText>(R.id.quantita_prodotto)
        quantityC.setText(cart.quantity!!.toString())
        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { _, _ ->

            firebaseDatabase.getReference("Utenti/$idU/Carrello/${cart.pName}").child("quantity").setValue(quantityC.text.toString())
            Toast.makeText(context, "Quantita modificata con successo", Toast.LENGTH_LONG).show()

        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
    }


}