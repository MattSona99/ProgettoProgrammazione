package com.example.progettoprogrammazione.dipendente

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.DipendenteCardBinding
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Restaurant

class DipendentetViewHolder(
    private val dipendenteBinding: DipendenteCardBinding,
    private val clickListener: DipendenteClickListener
    )
    :RecyclerView.ViewHolder(dipendenteBinding.root) {

    fun bindDipendente(dipendente: Dipendente){
        /*
        restaurantBinding.copertina.setImageResource( (dipendente.imageR)!!.toInt() )
        restaurantBinding.nomeRistorante.text= dipendente.nomeR;
        restaurantBinding.descrizione.text= dipendente.descrizioneR;
         */
        dipendenteBinding.btncard.setOnClickListener { clickListener.onClickDipendente(dipendente) }
    }

}