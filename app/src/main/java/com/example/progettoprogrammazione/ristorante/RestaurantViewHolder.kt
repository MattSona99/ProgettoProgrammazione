package com.example.progettoprogrammazione.ristorante

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Restaurant

class RestaurantViewHolder(
    private val restaurantBinding: RestaurantCardBinding,
    private val clickListener: RestaurantClickListener
    )
    :RecyclerView.ViewHolder(restaurantBinding.root) {

    fun bindRestaurants(restaurant: Restaurant){
        restaurantBinding.copertina.setImageResource( getImageId(restaurantBinding.root.context,restaurant.imageR!!) )
        restaurantBinding.nomeRistorante.text= restaurant.nomeR;
        restaurantBinding.descrizione.text= restaurant.descrizioneR;

        restaurantBinding.btncard.setOnClickListener { clickListener.onClickResturant(restaurant) }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }
}