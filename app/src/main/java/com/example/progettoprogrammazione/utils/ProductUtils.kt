package com.example.progettoprogrammazione.utils

import android.content.Context
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface ProductUtils {

    var firebaseAuth: FirebaseAuth
    var firebaseDatabase: FirebaseDatabase


}