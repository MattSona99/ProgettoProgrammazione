package com.example.progettoprogrammazione.utils

import android.content.Context
import android.widget.Toast
import com.example.progettoprogrammazione.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface UserUtil {
    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase

    //fun putUserData()

    //fun deleteUserData()

    fun updateUserData(
        callBack: FireBaseCallbackUser, context: Context?,
        UpdateMap: HashMap<String, Any>
    ){

        firebaseDatabase.getReference("Utenti").child(firebaseAuth.currentUser!!.uid)
            .updateChildren(UpdateMap)
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