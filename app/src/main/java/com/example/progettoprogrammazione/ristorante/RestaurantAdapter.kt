package com.example.progettoprogrammazione.ristorante

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardRestaurantBinding
import com.example.progettoprogrammazione.models.Restaurant

class RestaurantAdapter(
    private var restaurant: ArrayList<Restaurant>,
    private val clickListener: RestaurantClickListener
) :
    RecyclerView.Adapter<RestaurantViewHolder>() {

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
