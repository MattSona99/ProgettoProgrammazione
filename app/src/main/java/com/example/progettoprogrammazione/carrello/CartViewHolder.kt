package com.example.progettoprogrammazione.carrello

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CartCardBinding
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartViewHolder(
    private val cartBinding: CartCardBinding
) : RecyclerView.ViewHolder(cartBinding.root), ShoppingCartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindCart(cart: Cart,context: Context) {
        /*
        cartBinding.seekbarC.progress = cart.quantity.toString().toInt()
        cartBinding.quantityC.text = cart.quantity.toString()
         */

        cartBinding.nomeProdottoCardCart.text = cart.pName

        cartBinding.btnElimina.setOnClickListener{
            removeShoppingCart(cart,FirebaseAuth.getInstance().uid,context)
        }
        cartBinding.btnModifica.setOnClickListener{
            modifyProdC(cart,FirebaseAuth.getInstance().uid,context )
        }



    }


}