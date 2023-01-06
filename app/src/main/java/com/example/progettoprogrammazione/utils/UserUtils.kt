package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface UserUtils {
    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    fun deleteUserData(context: Context?) {
        firebaseAuth.currentUser!!.delete()
        firebaseDatabase.getReference("Utenti").child(firebaseAuth.currentUser!!.uid)
            .removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Utente eliminato con successo.",
                        Toast.LENGTH_LONG
                    ).show()
                } else Toast.makeText(
                    context,
                    "Errore durante l'eliminazione dell'account.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
    }


    fun updateUserData(
        context: Context?,
        UpdateMap: HashMap<String, Any>
    ) {

        firebaseDatabase.getReference("Utenti").child(firebaseAuth.currentUser!!.uid)
            .updateChildren(UpdateMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Update effettuato con successo.", Toast.LENGTH_LONG)
                        .show()
                } else Toast.makeText(
                    context,
                    "Errore durante l'update dei dati.",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
    }

    fun updateUserPassword(context: Context?, newpassword: String, email: String) {
        firebaseAuth.currentUser!!.updatePassword(newpassword)
    }

    fun recoverUserPassword(context: Context?, email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Email di recupero inviata.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getUserData(callBack: FireBaseCallbackUser, context: Context?) {

        firebaseDatabase.getReference("Utenti").child(firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = ResponseUser()
                    response.user = User(
                        snapshot.child("Nome").value.toString(),
                        snapshot.child("Cognome").value.toString(),
                        snapshot.child("Email").value.toString(),
                        snapshot.child("Password").value.toString(),
                        snapshot.child("Telefono").value.toString(),
                        snapshot.child("Uri").value.toString(),
                        snapshot.child("Livello").value.toString()
                    )
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
}