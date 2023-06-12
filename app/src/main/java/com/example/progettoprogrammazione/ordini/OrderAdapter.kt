package com.example.progettoprogrammazione.ordini

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardOrderBinding
import com.example.progettoprogrammazione.models.Order

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare una lista di "Order" ad una recyclerview
// Le funzioni sono override di funzionalità di base di un Adapter

class OrderAdapter(
    private var orders: ArrayList<Order>,
    private val clickListener: OrderClickListener
) : RecyclerView.Adapter<OrderViewHolder>() {

    // Effettua il binding della classe "OrderViewHolder"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CardOrderBinding.inflate(itemView, parent, false)
        return OrderViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindOrders(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
