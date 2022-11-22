package com.example.progettoprogrammazione.activity

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ResturantCardBinding

class ResturantViewHolder(
    private val resturantBinding: ResturantCardBinding,
    private val clickListener: ResturantClickListener
    )
    :RecyclerView.ViewHolder(resturantBinding.root) {
    fun bindResturants(resturant: Resturant){
        resturantBinding.copertina.setImageResource(resturant.image_r)
        resturantBinding.nomeRistorante.text= resturant.nome_r;
        resturantBinding.descrizione.text= resturant.descrizione_r;

        resturantBinding.cardView.setOnClickListener { clickListener.onClick(resturant) }
    }
}