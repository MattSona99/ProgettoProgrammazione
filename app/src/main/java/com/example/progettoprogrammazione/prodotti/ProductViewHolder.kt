package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.EmployeeActivity
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.models.Product

class ProductViewHolder(
    private val prodottoBinding: ProductCardBinding,
    private val clickListener: ProductClickListener
) :  RecyclerView.ViewHolder(prodottoBinding.root) {

    fun bindProdotti(prodotto: Product) {

        prodottoBinding.nomeProdottoCard.text=prodotto.descrizioneP

        prodottoBinding.btncard.setOnClickListener { clickListener.onClickProduct(prodotto) }

        prodottoBinding.cartQuantity.isVisible=false

        prodottoBinding.gotoCart.setOnClickListener {
            //CAMBIA VISIBILITA' BOTTONE E STARTA ACTIVITY/FRAG CARRELLO
            prodottoBinding.cartBegin.isVisible=false
            //SECONDO LAYOUT VISIBILE
            prodottoBinding.cartQuantity.isVisible=true
            var quantity=1
            var prezzoSingle=prodotto.prezzoP!!.toFloat()

            val shoppingCart=
                Cart(quantity,
                    prezzoSingle,
                    prodotto.idP.toString())

            prodottoBinding.cartNumber.text= shoppingCart.quantity.toString()

            prodottoBinding.btnAdd.setOnClickListener {
                quantity++;
            }
            prodottoBinding.btnRemove.setOnClickListener {
                quantity--;
            }

            var Prezzotot=prodotto.prezzoP!!.toFloat() * quantity.toFloat()

            val bundle=Bundle()
            bundle.putString("prodID",prodotto.idP.toString())
            bundle.putString("numpezzi",quantity.toString())
            bundle.putString("prezzo",Prezzotot.toString())

            //view?.findNavController()?.navigate(R.id.shopping_cart, bundle)

        }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }


}