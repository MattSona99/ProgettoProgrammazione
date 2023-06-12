package com.example.progettoprogrammazione.ordini

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardOrderProductBinding

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare una lista di "HasMap<String, String>" ad una recyclerview
// Le funzioni sono override di funzionalità di base di un Adapter

class OrderProductAdapter(
    private var orderProducts: ArrayList<HashMap<String, String>>
) : RecyclerView.Adapter<OrderProductViewHolder>() {

    // Effettua il binding della classe "OrderProductViewHolder"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CardOrderProductBinding.inflate(itemView, parent, false)
        return OrderProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        holder.bindOrderProduct(orderProducts[position])
    }

    override fun getItemCount(): Int {
        return orderProducts.size
    }


}