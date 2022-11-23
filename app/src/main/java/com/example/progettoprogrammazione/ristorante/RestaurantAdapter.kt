package com.example.progettoprogrammazione.ristorante

import android.app.AppComponentFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.google.android.material.imageview.ShapeableImageView

class RestaurantAdapter (
    private val restaurant: ArrayList<Restaurant>,
 //   private val clickListener: RestaurantClickListener
):
    RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_card,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = restaurant[position]
        holder.image_r.setImageResource(currentItem.image_r)
        holder.nome_r.text = currentItem.nome_r
        holder.descrizioneR.text = currentItem.descrizioneR
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image_r : AppCompatImageView = itemView.findViewById(R.id.copertina)
        val nome_r : TextView = itemView.findViewById(R.id.nome_ristorante)
        val descrizioneR : TextView = itemView.findViewById(R.id.descrizione)

    }
/*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val from=LayoutInflater.from(parent.context)
        val binding=RestaurantCardBinding.inflate(from, parent,false)
        return RestaurantViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindRestaurants(restaurant[position])
    }

    override fun getItemCount(): Int=restaurant.size

*/
}