package com.example.progettoprogrammazione.ristorante

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RestaurantViewHolder(
    private val restaurantBinding: RestaurantCardBinding,
    private val clickListener: RestaurantClickListener
) : RecyclerView.ViewHolder(restaurantBinding.root) {

    fun bindRestaurants(restaurant: Restaurant) {
        val imageName = restaurant.imageR
        val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
        val localfile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            restaurantBinding.copertina.setImageBitmap(bitmap)
        }

        restaurantBinding.copertina.setImageResource(
            getImageId(
                restaurantBinding.root.context,
                restaurant.imageR!!
            )
        )
        restaurantBinding.nomeRistorante.text =
            restaurant.nomeR!!.substring(0, 1).uppercase() + restaurant.nomeR!!.substring(1)
        restaurantBinding.rating.text = restaurant.ratingR.toString()
        restaurantBinding.descrizione.text = restaurant.descrizioneR!!.substring(0, 1)
            .uppercase() + restaurant.descrizioneR!!.substring(1)
        if (restaurant.tipoCiboR?.length!! > 19)
            restaurantBinding.tipocibo.text = restaurant.tipoCiboR?.substring(0, 18) + " ..."
        else
            restaurantBinding.tipocibo.text = restaurant.tipoCiboR
        restaurantBinding.btncard.setOnClickListener { clickListener.onClickResturant(restaurant) }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }

}