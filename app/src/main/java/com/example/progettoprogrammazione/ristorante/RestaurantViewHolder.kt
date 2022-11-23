package com.example.progettoprogrammazione.ristorante

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding

class RestaurantViewHolder(
    private val restaurantBinding: RestaurantCardBinding,
    private val clickListener: RestaurantClickListener
    )
    :RecyclerView.ViewHolder(restaurantBinding.root) {
    fun bindRestaurants(restaurant: Restaurant){
        restaurantBinding.copertina.setImageResource(restaurant.image_r)
        restaurantBinding.nomeRistorante.text= restaurant.nome_r;
        restaurantBinding.descrizione.text= restaurant.descrizione_r;

        restaurantBinding.cardView.setOnClickListener { clickListener.onClick(restaurant) }
    }
}