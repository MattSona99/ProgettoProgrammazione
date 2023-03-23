package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardModificaEliminaBinding
import com.example.progettoprogrammazione.models.Product


class ProductEMAdapter(
    private val prodotti: ArrayList<Product>,
    private val clickListener: ProductClickListener,
    private val restID: String,
    private val tipo: String,
    private val context: Context

) :
    RecyclerView.Adapter<ProductEMViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductEMViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = ProductCardModificaEliminaBinding.inflate(itemView, parent, false)

        return ProductEMViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ProductEMViewHolder, position: Int) {
        holder.bindProdotti(prodotti[position], restID, tipo, context)
    }

    override fun getItemCount(): Int {
        return prodotti.size
    }

}