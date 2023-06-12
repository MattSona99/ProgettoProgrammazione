package com.example.progettoprogrammazione.ordini

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardOrderProductBinding
import com.example.progettoprogrammazione.models.Order
import com.example.progettoprogrammazione.utils.OrderUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare una "HashMap<String, String>" in un file xml

class OrderProductViewHolder(
    private var orderProductCardBinding: CardOrderProductBinding,
) : RecyclerView.ViewHolder(orderProductCardBinding.root) {

    fun bindOrderProduct(order: HashMap<String, String>) {
        orderProductCardBinding.nomeProdottoOrdine.text = order["pname"].toString()
        orderProductCardBinding.quantitaProdottoOrdine.text = order["quantity"].toString()
    }
}