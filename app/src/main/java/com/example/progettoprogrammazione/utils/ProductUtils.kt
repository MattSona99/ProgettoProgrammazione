package com.example.progettoprogrammazione.utils


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentAddToMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

interface ProductUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun getBevanda(idR: String?, callBack: FireBaseCallbackProdotto, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Bevande")
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

    fun getAntipasto(idR: String?, callBack: FireBaseCallbackProdotto, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Antipasti")
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

    fun getPrimo(idR: String?, callBack: FireBaseCallbackProdotto, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Primi")
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

    fun getSecondo(idR: String?, callBack: FireBaseCallbackProdotto, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Secondi")
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

    fun getContorno(idR: String?, callBack: FireBaseCallbackProdotto, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Contorni")
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

    fun getDolce(idR: String?, callBack: FireBaseCallbackProdotto, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Dolci")
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


    fun addBevanda(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Bevande").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Bevanda aggiunta con successo.", Toast.LENGTH_LONG).show()
            }
    }

    fun addAntipasto(idR: String?, pData: Product, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/Antipasti").child(pData.nomeP!!)
            .setValue(pData).addOnSuccessListener {
                Toast.makeText(context, "Antipasto aggiunto con successo.", Toast.LENGTH_LONG)
                    .show()
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

    fun deleteProd(prodotto: Product, idR: String?, tipo: String?, context: Context?) {
        firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo").child(prodotto.nomeP!!)
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


    fun modifyProd(prodotto: Product, idR: String?, tipo: String?, context: Context?) {

        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.fragment_add_to_menu, null)
        val addDialog = AlertDialog.Builder(context)
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        nomeP.setText(prodotto.nomeP)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        prezzoP.setText(prodotto.prezzoP)
        val descrizioneP = v.findViewById<EditText>(R.id.descrizione_prodotto)
        descrizioneP.setText(prodotto.descrizioneP)
        val nomepath = prodotto.nomeP
        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { _, _ ->

            firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo/$nomepath").child("nomeP").setValue(nomeP.text.toString())
            firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo/$nomepath").child("prezzoP").setValue(prezzoP.text.toString())
            firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo/$nomepath").child("descrizioneP").setValue(descrizioneP.text.toString())
           // firebaseDatabase.getReference("Ristoranti/$idR/Menu/$tipo").child(prodotto.nomeP!!).setValue()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
    }

    }

