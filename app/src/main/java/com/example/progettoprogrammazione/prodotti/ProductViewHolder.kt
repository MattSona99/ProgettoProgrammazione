package com.example.progettoprogrammazione.prodotti

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardBinding
import com.example.progettoprogrammazione.models.Product

class ProductViewHolder(
    private val prodottoBinding: ProductCardBinding,
    private val clickListener: ProductClickListener
) : RecyclerView.ViewHolder(prodottoBinding.root) {

    fun bindProdotti(prodotto: Product) {
        prodottoBinding.descrizione.text=prodotto.descrizioneP

        prodottoBinding.btncard.setOnClickListener { clickListener.onClickProduct(prodotto) }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }

}