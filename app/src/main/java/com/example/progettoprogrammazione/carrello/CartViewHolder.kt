package com.example.progettoprogrammazione.carrello

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.CardCartBinding
import com.example.progettoprogrammazione.databinding.Fragment1CarrelloBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.utils.CartUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare un "Product" in un file xml

class CartViewHolder(
    private val cartBinding: CardCartBinding
) : RecyclerView.ViewHolder(cartBinding.root), CartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindCart(
        cartProduct: CartProduct,
        cartViewModel: CartViewModel,
        bindingCarrello: Fragment1CarrelloBinding,
        context: Context
    ) {

        cartBinding.nomeProdottoCardC.text = cartProduct.pName
        cartBinding.cardDescC.text = cartProduct.pDesc
        cartBinding.quantityC.text = cartProduct.quantity.toString()

        // Funzione che permette di eliminare il prodotto dalla lista
        cartBinding.deleteProductC.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Conferma l'eliminazione del prodotto")
            builder.setMessage("Sei sicuro di voler eliminare il prodotto dal carrello?")
            builder.setPositiveButton("Sì") { dialog, _ ->
                cartViewModel.deleteSpecificCart(cartProduct)
                if (cartViewModel.getcartItems().value!!.isEmpty()) {
                    cartViewModel.deleteCartItems()
                    bindingCarrello.totCarrelloLayout.isGone = true
                    bindingCarrello.noProduct.isVisible = true
                    bindingCarrello.constraintQR.isGone = true
                    dialog.cancel()
                }
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }
}
