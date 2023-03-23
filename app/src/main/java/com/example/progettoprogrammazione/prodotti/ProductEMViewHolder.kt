package com.example.progettoprogrammazione.prodotti

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.ProductCardModificaEliminaBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.ProductUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProductEMViewHolder(
    private val prodottoBinding: ProductCardModificaEliminaBinding,
    private val clickListener: ProductClickListener

) : RecyclerView.ViewHolder(prodottoBinding.root), ProductUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindProdotti(prodotto: Product, idR: String?, tipo: String?, context: Context) {
        prodottoBinding.nomeProdottoCard.text = prodotto.nomeP
        prodottoBinding.clickDetail.setOnClickListener { clickListener.onClickProduct(prodotto) }

        prodottoBinding.btnElimina.setOnClickListener{
            deleteProd(prodotto, idR, tipo, context)
        }
        prodottoBinding.btnModifica.setOnClickListener{
            modifyProd(prodotto, idR, tipo)
        }

    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }


}