package com.example.progettoprogrammazione.carrello

import android.content.Context
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CartCardBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.utils.CartUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartViewHolder(
    private val cartBinding: CartCardBinding
) : RecyclerView.ViewHolder(cartBinding.root), CartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindCart(cartProduct: CartProduct, cartViewModel: CartViewModel) {

        cartBinding.seekbarC.progress = cartProduct.quantity!!
        cartBinding.seekbarC.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                cartBinding.quantityC.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        cartBinding.nomeProdottoCardC.text = cartProduct.pName
        cartBinding.cardDescC.text = cartProduct.pDesc
        cartBinding.quantityC.text = cartProduct.quantity.toString()

        cartBinding.deleteProductC.setOnClickListener {
            cartViewModel.deleteSpecificCart(cartProduct)
            if (cartViewModel.getcartItems().value!!.isEmpty()) {
                cartViewModel.deleteCartItems()
            }
        }
    }
}