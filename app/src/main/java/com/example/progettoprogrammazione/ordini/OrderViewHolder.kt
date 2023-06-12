package com.example.progettoprogrammazione.ordini

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardOrderBinding
import com.example.progettoprogrammazione.models.Order
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare un "Order" in un file xml

class OrderViewHolder(
    private val orderBinding: CardOrderBinding,
    private val clickListener: OrderClickListener
) : RecyclerView.ViewHolder(orderBinding.root) {
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindOrders(order: Order) {
        orderBinding.numeroOrdine.text = "Ordine numero: " + order.numero.toString()
        orderBinding.checkboxOrdine.isChecked = order.checked
        orderBinding.clickOrder.setOnClickListener {
            clickListener.onClickOrder(order)
        }

        // Consente di mantenere in memoria se un ordine è stato effettuato o meno
        orderBinding.checkboxOrdine.setOnClickListener {
            firebaseDatabase.getReference("Ristoranti/${order.rID}/Ordini/${order.numero}/checked")
                .setValue(orderBinding.checkboxOrdine.isChecked)
        }
    }
}