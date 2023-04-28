package com.example.progettoprogrammazione.ordini

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.OrderCardBinding
import com.example.progettoprogrammazione.models.Order

class OrderViewHolder(
    private val orderBinding: OrderCardBinding,
    private val clickListener: OrderClickListener
) : RecyclerView.ViewHolder(orderBinding.root) {

    fun bindOrders(order: Order) {
        orderBinding.numeroOrdine.text = "Ordine numero: " + order.numero.toString()
        orderBinding.clickOrder.setOnClickListener {
            clickListener.onClickOrder(order)
        }
    }

}