package com.example.progettoprogrammazione.prodotti

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardProductModificaBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.ProductUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProductEMViewHolder(
    private val prodottoBinding: CardProductModificaBinding,

) : RecyclerView.ViewHolder(prodottoBinding.root), ProductUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindProdotti(prodotto: Product, idR: String?, tipo: String?, context: Context) {
        prodottoBinding.nomeProdottoCardM.text = prodotto.nomeP
        prodottoBinding.cardDescM.text = prodotto.descrizioneP

        prodottoBinding.btnEliminaM.setOnClickListener{
            deleteProd(prodotto, idR, tipo, context)
        }
        prodottoBinding.btnModificaM.setOnClickListener{
            modifyProd(prodotto, idR, tipo,context)
        }

    }

}