package com.example.progettoprogrammazione.ristorante

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Restaurant

class RestaurantAdapter(
    private val restaurant: ArrayList<Restaurant>,
    private val clickListener: RestaurantClickListener
) :
    RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = RestaurantCardBinding.inflate(itemView, parent, false)
        return RestaurantViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindRestaurants(restaurant[position])
/*
            val currentItem = restaurant[position]

            holder.image_r.setImageResource(currentItem.imageR)
            holder.nome_r.text = currentItem.nomeR
            holder.descrizioneR.text = currentItem.descrizioneR
             */
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }

    /*
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_r: AppCompatImageView = itemView.findViewById(R.id.copertina)
        val nome_r: TextView = itemView.findViewById(R.id.nome_ristorante)
        val descrizioneR: TextView = itemView.findViewById(R.id.descrizione)
    }
     */


}