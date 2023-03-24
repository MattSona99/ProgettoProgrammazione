package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.databinding.ProductCardModificaEliminaBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.ResponseShoppingCart
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProductViewHolder(
    private val prodottoBinding: ProductCardBinding,
    private val clickListener: ProductClickListener

) : RecyclerView.ViewHolder(prodottoBinding.root), ShoppingCartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindProdotti(prodotto: Product) {

        prodottoBinding.nomeProdottoCard.text = prodotto.nomeP

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

    fun createShoppingCart(prodotto: Product) {
        prodottoBinding.cartQuantity.isVisible = false

        prodottoBinding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                prodottoBinding.quantity.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        prodottoBinding.addProduct.setOnClickListener {
            val quantity = prodottoBinding.quantity.toString().toInt()

            val prezzoTot = prodotto.prezzoP!!.toFloat() * quantity

            val shoppingCart =
                Cart(
                    prodotto.nomeP,
                    quantity,
                    prezzoTot,
                    prodotto.idP
                )
            addquantityShoppingCart(shoppingCart, quantity, FirebaseAuth.getInstance().uid)

            /*prodottoBinding.btnRemove.setOnClickListener {
                removequantityShoppingCart(
                    shoppingCart,
                    quantity,
                    FirebaseAuth.getInstance().uid
                )

            }*/

            //prodottoBinding.checkout.setOnClickListener{}
        }
    }


    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }


}