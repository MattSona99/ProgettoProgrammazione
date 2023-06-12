package com.example.progettoprogrammazione.carrello

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardCartBinding
import com.example.progettoprogrammazione.databinding.Fragment1CarrelloBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.viewmodels.CartViewModel

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare una lista di "CartProduct" ad una recyclerview
// Le funzioni sono override di funzionalità di base di un Adapter

class CartAdapter(
    private var cartProducts: ArrayList<CartProduct>,
    private var cartViewModel: CartViewModel,
    private var bindingCarrello: Fragment1CarrelloBinding,
    private var context: Context
) : RecyclerView.Adapter<CartViewHolder>() {

    // Adatta la lista e controlla se sono stati effettuati dei cambiamenti
    fun setData(cartProductList: ArrayList<CartProduct>) {
        this.cartProducts = cartProductList
        notifyDataSetChanged()
    }

    // Effettua il binding della classe "CartViewHolder"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CardCartBinding.inflate(itemView, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindCart(cartProducts[position], cartViewModel, bindingCarrello, context)

    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }
}