package com.example.progettoprogrammazione.shoppingcart

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ShoppingCartViewHolder(
    private val binding: ProductCardBinding

    ) : RecyclerView.ViewHolder(binding.root), ShoppingCartUtils {
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindCarrello(cart: String) {
        binding.nomeProdottoCard.text = cart

    }
}