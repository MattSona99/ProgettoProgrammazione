package com.example.progettoprogrammazione.utils

import android.content.Context
import com.google.firebase.database.FirebaseDatabase

interface RestaurantUtils {

    var firebaseDatabase:FirebaseDatabase

    fun createRestaurant(callbackRestaurant: FireBaseCallbackRestaurant ,context: Context?){
        firebaseDatabase.getReference("Ristoranti")
    }
}