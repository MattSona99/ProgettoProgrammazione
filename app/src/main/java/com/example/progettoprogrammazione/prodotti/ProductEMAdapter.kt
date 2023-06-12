package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardProductModificaBinding
import com.example.progettoprogrammazione.models.Product

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare una lista di "Product" ad una recyclerview
// Le funzioni sono override di funzionalità di base di un Adapter

class ProductEMAdapter(
    private var prodotti: ArrayList<Product>,
    private val restID: String,
    private val tipo: String,
    private val context: Context
) :
    RecyclerView.Adapter<ProductEMViewHolder>() {

    // Adatta la lista e controlla se sono stati effettuati dei cambiamenti
    fun setData(productList: ArrayList<Product>) {
        this.prodotti = productList
        notifyDataSetChanged()
    }

    // Effettua il binding della classe "ProductEMViewHolder"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductEMViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CardProductModificaBinding.inflate(itemView, parent, false)

        return ProductEMViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductEMViewHolder, position: Int) {
        holder.bindProdotti(prodotti[position], restID, tipo, context)
    }

    override fun getItemCount(): Int {
        return prodotti.size
    }

}