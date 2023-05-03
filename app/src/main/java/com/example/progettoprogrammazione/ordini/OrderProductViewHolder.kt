package com.example.progettoprogrammazione.ordini

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardOrderProductBinding
import com.example.progettoprogrammazione.models.Order
import org.json.JSONObject

class OrderProductViewHolder(
    private var orderProductCardBinding: CardOrderProductBinding
) : RecyclerView.ViewHolder(orderProductCardBinding.root) {

    fun bindOrderProduct(order: HashMap<String, String>) {
        orderProductCardBinding.nomeProdottoOrdine.text = order["pname"].toString()
        orderProductCardBinding.quantitaProdottoOrdine.text = order["quantity"].toString()
    }

}