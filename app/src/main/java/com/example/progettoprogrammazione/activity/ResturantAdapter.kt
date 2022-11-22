package com.example.progettoprogrammazione.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ResturantCardBinding

class ResturantAdapter (
    private val resturant: List<Resturant>,
    private val clickListener: ResturantClickListener):
    RecyclerView.Adapter<ResturantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResturantViewHolder {
        val from=LayoutInflater.from(parent.context)
        val binding=ResturantCardBinding.inflate(from, parent,false)
        return ResturantViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: ResturantViewHolder, position: Int) {
        holder.bindResturants(resturant[position])
    }

    override fun getItemCount(): Int=resturant.size;


}