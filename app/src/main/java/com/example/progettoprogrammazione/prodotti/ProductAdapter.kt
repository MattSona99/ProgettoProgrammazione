package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.viewmodels.CartViewModel

class ProductAdapter(
    private var prodotti: ArrayList<Product>,
    private val context: Context,
    private val cartViewModel: CartViewModel,
    private val restID: String
) :
    RecyclerView.Adapter<ProductViewHolder>() {

    fun setData(productList: ArrayList<Product>) {
        this.prodotti = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = ProductCardBinding.inflate(itemView, parent, false)
        return ProductViewHolder(binding, cartViewModel)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProdotti(prodotti[position])
        holder.createShoppingCart(prodotti[position], restID, context)

    }

    override fun getItemCount(): Int {
        return prodotti.size
    }


}