package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.QRCodeUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProductViewHolder(
    private val prodottoBinding: ProductCardBinding,
    private val cartViewModel: CartViewModel

) : RecyclerView.ViewHolder(prodottoBinding.root), QRCodeUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindProdotti(prodotto: Product) {
        prodottoBinding.nomeProdottoCard.text = prodotto.nomeP
        prodottoBinding.cardDesc.text = prodotto.descrizioneP
    }

    fun createShoppingCart(prodotto: Product, context: Context) {

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
            val prezzoTot = prodotto.prezzoP!!.toFloat() * quantity
            val shoppingCartProduct =
                CartProduct(
                    prodotto.nomeP,
                    prodotto.descrizioneP,
                    quantity,
                    prezzoTot,
                    prodotto.idP,
                )
            if (quantity != 0) {
                cartViewModel.addcartItems(shoppingCartProduct)
                Toast.makeText(context, "Prodotto aggiunto al carrello.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Seleziona una quantità maggiore di 0.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}