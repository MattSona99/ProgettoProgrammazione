package com.example.progettoprogrammazione.ristorante

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardRestaurantBinding
import com.example.progettoprogrammazione.models.Restaurant

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare una lista di "Restaurant" ad una recyclerview
// Le funzioni sono override di funzionalità di base di un Adapter

class RestaurantAdapter(
    private var restaurant: ArrayList<Restaurant>,
    private val clickListener: RestaurantClickListener
) :
    RecyclerView.Adapter<RestaurantViewHolder>() {

    // Effettua il binding della classe "RestaurantViewHolder"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CardRestaurantBinding.inflate(itemView, parent, false)
        return RestaurantViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindRestaurants(restaurant[position])
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }


}
