package com.example.progettoprogrammazione.carrello

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CartCardBinding
import com.example.progettoprogrammazione.models.Cart

class CartAdapter(
    private var carts: ArrayList<Cart>,
    private val context: Context
) : RecyclerView.Adapter<CartViewHolder>() {

    fun setData(cartList: ArrayList<Cart>) {
        this.carts = cartList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = CartCardBinding.inflate(itemView, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindCart(carts[position])

    }

    override fun getItemCount(): Int {
        return carts.size
    }
}