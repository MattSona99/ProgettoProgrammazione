package com.example.progettoprogrammazione.ristoratore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentMenuBinding
import com.example.progettoprogrammazione.databinding.FragmentModificaMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.prodotti.ProductAdapter
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentModificaMenu : Fragment(), UserUtils {

    private lateinit var binding: FragmentModificaMenuBinding
    private lateinit var adapterBev: ProductAdapter
    private lateinit var adapterAnt: ProductAdapter
    private lateinit var adapterPri: ProductAdapter
    private lateinit var adapterSec: ProductAdapter
    private lateinit var adapterCon: ProductAdapter
    private lateinit var adapterDol: ProductAdapter

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var bevandeArrayList: ArrayList<Product>
    private lateinit var antipastiArrayList: ArrayList<Product>
    private lateinit var primiArrayList: ArrayList<Product>
    private lateinit var secondiArrayList: ArrayList<Product>
    private lateinit var contorniArrayList: ArrayList<Product>
    private lateinit var dolciArrayList: ArrayList<Product>

    private lateinit var userlvl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModificaMenuBinding.inflate(layoutInflater)
        val args = this.arguments
        val proprietarioR = args?.get("proprietarioR")

        getUserData(object : FireBaseCallbackUser {
            override fun onResponse(responseU: ResponseUser) {
                userlvl = responseU.user!!.Livello.toString()
                if (userlvl == "3" && proprietarioR==responseU.user!!.Email)
                    binding.btnModifica.isVisible = true
            }
        }, context)
        return inflater.inflate(R.layout.fragment_modifica_menu, container, false)
    }

}