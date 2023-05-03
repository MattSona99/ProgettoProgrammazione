package com.example.progettoprogrammazione.ordini

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardOrderBinding
import com.example.progettoprogrammazione.models.Order

class OrderViewHolder(
    private val orderBinding: CardOrderBinding,
    private val clickListener: OrderClickListener
) : RecyclerView.ViewHolder(orderBinding.root) {

    fun bindOrders(order: Order) {
        orderBinding.numeroOrdine.text = "Ordine numero: " + order.numero.toString()
        orderBinding.clickOrder.setOnClickListener {
            clickListener.onClickOrder(order)
        }
    }

}