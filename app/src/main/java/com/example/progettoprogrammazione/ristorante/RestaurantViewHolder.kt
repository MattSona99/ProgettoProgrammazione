package com.example.progettoprogrammazione.ristorante

import android.content.Context
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardRestaurantBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare un "Restaurant" in un file xml

class RestaurantViewHolder(
    private val restaurantBinding: CardRestaurantBinding,
    private val clickListener: RestaurantClickListener,
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
) : RecyclerView.ViewHolder(restaurantBinding.root), RestaurantUtils {

    fun bindRestaurants(restaurant: Restaurant) {

        val imageName = restaurant.imageR
        val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
        val localfile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            restaurantBinding.copertina.setImageBitmap(bitmap)
        }

        restaurantBinding.nomeRistorante.text =
            restaurant.nomeR!!.substring(0, 1).uppercase() + restaurant.nomeR!!.substring(1)
        if (restaurant.descrizioneR?.length!! > 40) {
            restaurantBinding.descrizione.text = restaurant.descrizioneR!!.substring(0, 1)
                .uppercase() + restaurant.descrizioneR!!.substring(1, 39) + "..."
        } else
            restaurantBinding.descrizione.text = restaurant.descrizioneR!!.substring(0, 1)
                .uppercase() + restaurant.descrizioneR!!.substring(1)
        if (restaurant.tipoCiboR?.length!! > 25)
            restaurantBinding.tipocibo.text = restaurant.tipoCiboR?.substring(0, 24) + " ..."
        else
            restaurantBinding.tipocibo.text = restaurant.tipoCiboR
        restaurantBinding.btncard.setOnClickListener { clickListener.onClickRestaurant(restaurant) }
        restaurantBinding.rating.text = restaurant.ratingR.toString()

    }



}