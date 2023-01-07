package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProductViewHolder(
    private val prodottoBinding: ProductCardBinding,
    private val clickListener: ProductClickListener
) :  RecyclerView.ViewHolder(prodottoBinding.root),ShoppingCartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindProdotti(prodotto: Product) {

        prodottoBinding.nomeProdottoCard.text=prodotto.descrizioneP

        prodottoBinding.btncard.setOnClickListener { clickListener.onClickProduct(prodotto) }

    }
/*      PRIMO PROTOTIPO

    fun createShoppingCart(prodotto: Product, quantita: Int){

        prodottoBinding.cartQuantity.isVisible=false

        prodottoBinding.gotoCart.setOnClickListener {
            //CAMBIA VISIBILITA' BOTTONE E STARTA ACTIVITY/FRAG CARRELLO
            prodottoBinding.cartBegin.isVisible=false
            //SECONDO LAYOUT VISIBILE
            prodottoBinding.cartQuantity.isVisible=true
            
            var quantity=quantita+1
            val prezzoSingle=prodotto.prezzoP!!.toFloat()

            val shoppingCart=
                Cart(prodotto.nomeP.toString(),
                    quantity,
                    prezzoSingle,
                    prodotto.idP.toString())

            prodottoBinding.cartNumber.text= shoppingCart.quantity.toString()

            prodottoBinding.btnAdd.setOnClickListener {
                quantity++;
            }
            prodottoBinding.btnRemove.setOnClickListener {
                quantity--;
            }

            val Prezzotot=prodotto.prezzoP!!.toFloat() * quantity.toFloat()

        }

    }

 */
    fun createShoppingCart(prodotto: Product, quantita: Int){

        prodottoBinding.cartQuantity.isVisible=false

        prodottoBinding.gotoCart.setOnClickListener {
            //CAMBIA VISIBILITA' BOTTONE E STARTA ACTIVITY/FRAG CARRELLO
            prodottoBinding.cartBegin.isVisible=false
            //SECONDO LAYOUT VISIBILE
            prodottoBinding.cartQuantity.isVisible=true

            var quantity=quantita+1
            val prezzoSingle=prodotto.prezzoP!!.toFloat()

            prodottoBinding.btnAdd.setOnClickListener {

            }
            prodottoBinding.btnRemove.setOnClickListener {

            }

        }

    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }


}