package com.example.progettoprogrammazione.prodotti

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentCarrelloBinding
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.CartUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ProductViewHolder(
    private val prodottoBinding: ProductCardBinding,
    private val cartViewModel: CartViewModel,

) : RecyclerView.ViewHolder(prodottoBinding.root), CartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()


    fun bindProdotti(prodotto: Product) {
        prodottoBinding.nomeProdottoCard.text = prodotto.nomeP
        prodottoBinding.cardDesc.text = prodotto.descrizioneP
    }

    fun createShoppingCart(prodotto: Product, restID: String, context: Context) {

        prodottoBinding.seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                prodottoBinding.quantity.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        prodottoBinding.addProduct.setOnClickListener {
            val quantity = prodottoBinding.quantity.text.toString().toInt()
            if (quantity != 0) {
                val prezzoTot = prodotto.prezzoP!!.toFloat() * quantity
                val shoppingCartProduct =
                    CartProduct(
                        prodotto.nomeP,
                        prodotto.descrizioneP,
                        quantity,
                        prezzoTot,
                        restID,
                        prodotto.idP,
                    )
                val cart = cartViewModel.getcartItems().value

                if (cart!!.isEmpty()) {
                    cartViewModel.addcartItems(shoppingCartProduct)
                    Toast.makeText(
                        context,
                        "Prodotto aggiunto al carrello.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (cart[0].restID == shoppingCartProduct.restID) {
                        cartViewModel.addcartItems(shoppingCartProduct)
                        Toast.makeText(
                            context,
                            "Prodotto aggiunto al carrello.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("Hai selezionato un prodotto da un ristorante diverso!")
                        builder.setMessage("Desideri creare un nuovo carrello?")
                        builder.setPositiveButton("Sì") { dialog, _ ->
                            cartViewModel.deleteCartItems()
                            cartViewModel.addcartItems(shoppingCartProduct)
                            dialog.cancel()
                        }
                        builder.setNegativeButton("No") { dialog, _ ->

                            dialog.cancel()
                        }
                        builder.show()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Seleziona una quantità maggiore di 0.",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
        }
    }
}