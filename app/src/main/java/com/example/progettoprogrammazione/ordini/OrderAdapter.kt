package com.example.progettoprogrammazione.ordini

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.OrderCardBinding
import com.example.progettoprogrammazione.models.Order

class OrderAdapter(
    private var orders: ArrayList<Order>,
    private val clickListener: OrderClickListener
) : RecyclerView.Adapter<OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = OrderCardBinding.inflate(itemView, parent, false)
        return OrderViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindOrders(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
