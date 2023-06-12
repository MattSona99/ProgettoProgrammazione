package com.example.progettoprogrammazione.prodotti

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.CardProductModificaBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.utils.ProductUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe consente di adattare un "Product" in un file xml

class ProductEMViewHolder(
    private val prodottoBinding: CardProductModificaBinding,

) : RecyclerView.ViewHolder(prodottoBinding.root), ProductUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun bindProdotti(prodotto: Product, idR: String?, tipo: String?, context: Context) {
        prodottoBinding.nomeProdottoCardM.text = prodotto.nomeP
        prodottoBinding.cardDescM.text = prodotto.descrizioneP

        //Consente di eliminare il prodotto
        prodottoBinding.btnEliminaM.setOnClickListener{
            deleteProd(prodotto, idR, tipo, context)
        }

        //Consente di modificare il prodotto
        prodottoBinding.btnModificaM.setOnClickListener{
            modifyProd(prodotto, idR, tipo,context)
        }

    }

}