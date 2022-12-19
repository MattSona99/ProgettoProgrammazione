package com.example.progettoprogrammazione.dipendente

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.DipendenteCardBinding
import com.example.progettoprogrammazione.models.Dipendente

class DipendenteAdapter(
    private val dipendenti: ArrayList<Dipendente>,
    private val clickListener: DipendenteClickListener
) :
    RecyclerView.Adapter<DipendentetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DipendentetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = DipendenteCardBinding.inflate(itemView, parent, false)
        return DipendentetViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: DipendentetViewHolder, position: Int) {
        holder.bindDipendente(dipendenti[position])
            /*
            val currentItem = restaurant[position]

            holder.image_r.setImageResource(currentItem.imageR)
            holder.nome_r.text = currentItem.nomeR
            holder.descrizioneR.text = currentItem.descrizioneR
             */
    }

    override fun getItemCount(): Int {
        return dipendenti.size
    }

    /*
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_r: AppCompatImageView = itemView.findViewById(R.id.copertina)
        val nome_r: TextView = itemView.findViewById(R.id.nome_ristorante)
        val descrizioneR: TextView = itemView.findViewById(R.id.descrizione)
    }
     */


}