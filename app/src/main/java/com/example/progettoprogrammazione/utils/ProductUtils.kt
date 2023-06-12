package com.example.progettoprogrammazione.utils


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa interfaccia implementa determinati metodi per effettuare delle query con il database
// per il recupero, modifica o eliminazione di informazioni relative ai prodotti

interface ProductUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    // Funzione che recupera tutti i prodotti all'interno del menu di un ristorante specifico dal database
    fun getProdotti(
        idR: String?,
        callBack: FireBaseCallbackProdotto,
        tipo: String,
        context: Context?
    ) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseProdotto()
                    for (prod: DataSnapshot in snapshot.children) {
                        val prodotto = Product(
                            prod.child("nomeP").value.toString(),
                            prod.child("prezzoP").value.toString(),
                            prod.child("descrizioneP").value.toString(),
                            prod.child("idP").value.toString()
                        )
                        response.prodotto.add(prodotto)
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

    // Funzione che aggiunge un prodotto al menu di un ristorante specifico nel database
    fun addProdotto(idR: String?, pData: Product, tipo: String, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo").child(pData.idP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Prodotto aggiunto con successo.", Toast.LENGTH_LONG).show()
            }
    }

    // Funzione che elimina un prodtto all'interno del menu di un ristorante nel database
    fun deleteProd(prodotto: Product, idR: String?, tipo: String?, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo").child(prodotto.idP!!)
            .removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Prodotto eliminato con successo.",
                        Toast.LENGTH_LONG
                    ).show()
                } else Toast.makeText(
                    context,
                    "Errore durante l'eliminazione del prodotto.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
    }

    // Funzione che modifica un prodotto specifico all'interno del menu di un ristorante nel database
    fun modifyProd(prodotto: Product, idR: String?, tipo: String?, context: Context?) {

        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.fragment_r_add_to_menu, null)
        val addDialog = AlertDialog.Builder(context)
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        nomeP.setText(prodotto.nomeP)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        prezzoP.setText(prodotto.prezzoP)
        val descrizioneP = v.findViewById<EditText>(R.id.descrizione_prodotto)
        descrizioneP.setText(prodotto.descrizioneP)
        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { _, _ ->

            firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo/${prodotto.idP}")
                .child("nomeP").setValue(nomeP.text.toString())
            firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo/${prodotto.idP}")
                .child("prezzoP").setValue(prezzoP.text.toString())
            firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo/${prodotto.idP}")
                .child("descrizioneP").setValue(descrizioneP.text.toString())

        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
    }

}

