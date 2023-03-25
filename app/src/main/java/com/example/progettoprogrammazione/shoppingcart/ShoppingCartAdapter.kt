package com.example.progettoprogrammazione.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.prodotti.ProductClickListener

class ShoppingCartAdapter(
    private var itemsCart: HashMap<String,Cart>,
    private val clickListener: ProductClickListener
) :
    RecyclerView.Adapter<ShoppingCartViewHolder>() {

    fun setData(cart:  HashMap<String,Cart>) {
        this.itemsCart = cart
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = ProductCardBinding.inflate(itemView, parent, false)
        return ShoppingCartViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.bindCarrello(itemsCart.keys.toTypedArray()[position])
    }

    override fun getItemCount(): Int {
        return itemsCart.size
    }

}