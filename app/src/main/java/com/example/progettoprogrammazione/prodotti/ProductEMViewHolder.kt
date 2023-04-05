package com.example.progettoprogrammazione.prodotti

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.FragmentAddToMenuBinding
import com.example.progettoprogrammazione.databinding.ProductCardModificaEliminaBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.ProductUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.NonDisposableHandle.parent

class ProductEMViewHolder(
    private val prodottoBinding: ProductCardModificaEliminaBinding,

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