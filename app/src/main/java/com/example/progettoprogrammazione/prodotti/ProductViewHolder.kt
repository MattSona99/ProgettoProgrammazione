package com.example.progettoprogrammazione.prodotti

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardProductBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.CartUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare un "Product" in un file xml

class ProductViewHolder(
    private val prodottoBinding: CardProductBinding,
    private val cartViewModel: CartViewModel,

    ) : RecyclerView.ViewHolder(prodottoBinding.root), CartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()


    fun bindProdotti(prodotto: Product) {

        prodottoBinding.quantity.inputType = InputType.TYPE_CLASS_NUMBER

        prodottoBinding.nomeProdottoCard.text = prodotto.nomeP
        prodottoBinding.cardDesc.text = prodotto.descrizioneP
    }

    // Funzione che crea un carrello all'interno del viewmodel, aggiornato in tutta l'app
    fun createShoppingCart(prodotto: Product, restID: String, context: Context) {

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