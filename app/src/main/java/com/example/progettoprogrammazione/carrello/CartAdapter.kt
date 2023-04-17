package com.example.progettoprogrammazione.carrello

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CartCardBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.viewmodels.CartViewModel

class CartAdapter(
    private var cartProducts: ArrayList<CartProduct>,
    private var cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartViewHolder>() {

    fun setData(cartProductList: ArrayList<CartProduct>) {
        this.cartProducts = cartProductList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CartCardBinding.inflate(itemView, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindCart(cartProducts[position], cartViewModel)

    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }
}