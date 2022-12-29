package com.example.progettoprogrammazione.utils


import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.models.Product
import com.google.firebase.database.FirebaseDatabase

interface ProductUtils {

    var firebaseDatabase: FirebaseDatabase

    fun addBevanda(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Bevande").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Bevanda aggiunta con successo.", Toast.LENGTH_LONG).show()
            }
    }

    fun addAntipasto(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Antipasti").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Antipasto aggiunto con successo.", Toast.LENGTH_LONG).show()
            }
    }

    fun addPrimo(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Primi").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Primo aggiunto con successo.", Toast.LENGTH_LONG).show()
            }
    }

    fun addSecondo(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Secondi").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Secondo aggiunto con successo.", Toast.LENGTH_LONG).show()
            }
    }

    fun addContorno(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Contorni").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Contorno aggiunto con successo.", Toast.LENGTH_LONG).show()
            }
    }

    fun addDolce(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Dolci").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Dolce aggiunto con successo.", Toast.LENGTH_LONG).show()
            }
    }


}