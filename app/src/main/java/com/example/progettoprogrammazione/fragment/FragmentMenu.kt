package com.example.progettoprogrammazione.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.CreaMenu
import com.example.progettoprogrammazione.databinding.FragmentMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.prodotti.ProductAdapter
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentMenu : Fragment(), ProductClickListener, ProductUtils, UserUtils, RestaurantUtils {

    private lateinit var binding: FragmentMenuBinding
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
    private var prodotti = arrayListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        val args = this.arguments
        val proprietarioR = args?.get("proprietarioR")

        getUserData(object : FireBaseCallbackUser {
            override fun onResponse(responseU: ResponseUser) {
                userlvl = responseU.user!!.Livello.toString()
                if (userlvl == "3" && proprietarioR==responseU.user!!.Email)
                    binding.btnModifica.isVisible = true
            }
        }, context)


        bevandeArrayList = args?.getParcelableArrayList<Product>("bevande") as ArrayList<Product>
        antipastiArrayList = args.getParcelableArrayList<Product>("antipasti") as ArrayList<Product>
        primiArrayList = args.getParcelableArrayList<Product>("primi") as ArrayList<Product>
        secondiArrayList = args.getParcelableArrayList<Product>("secondi") as ArrayList<Product>
        contorniArrayList = args.getParcelableArrayList<Product>("contorni") as ArrayList<Product>
        dolciArrayList = args.getParcelableArrayList<Product>("dolci") as ArrayList<Product>

        val layoutManagerBev = GridLayoutManager(context, 2)
        binding.recycleViewBevande.layoutManager = layoutManagerBev
        adapterBev = ProductAdapter(bevandeArrayList, this)
        binding.recycleViewBevande.adapter = adapterBev
        binding.recycleViewBevande.setHasFixedSize(true)

        val layoutManagerAnt = GridLayoutManager(context, 2)
        binding.recycleViewAntipasti.layoutManager = layoutManagerAnt
        adapterAnt = ProductAdapter(antipastiArrayList, this)
        binding.recycleViewAntipasti.adapter = adapterAnt
        binding.recycleViewAntipasti.setHasFixedSize(true)

        val layoutManagerPri = GridLayoutManager(context, 2)
        binding.recycleViewPrimi.layoutManager = layoutManagerPri
        adapterPri = ProductAdapter(primiArrayList, this)
        binding.recycleViewPrimi.adapter = adapterPri
        binding.recycleViewPrimi.setHasFixedSize(true)

        val layoutManagerSec = GridLayoutManager(context, 2)
        binding.recycleViewSecondi.layoutManager = layoutManagerSec
        adapterSec = ProductAdapter(secondiArrayList, this)
        binding.recycleViewSecondi.adapter = adapterSec
        binding.recycleViewSecondi.setHasFixedSize(true)

        val layoutManagerCon = GridLayoutManager(context, 2)
        binding.recycleViewContorni.layoutManager = layoutManagerCon
        adapterCon = ProductAdapter(contorniArrayList, this)
        binding.recycleViewContorni.adapter = adapterCon
        binding.recycleViewContorni.setHasFixedSize(true)

        val layoutManagerDol = GridLayoutManager(context, 2)
        binding.recycleViewDolci.layoutManager = layoutManagerDol
        adapterDol = ProductAdapter(dolciArrayList, this)
        binding.recycleViewDolci.adapter = adapterDol
        binding.recycleViewDolci.setHasFixedSize(true)

        prodotti.addAll(bevandeArrayList)
        prodotti.addAll(antipastiArrayList)
        prodotti.addAll(primiArrayList)
        prodotti.addAll(secondiArrayList)
        prodotti.addAll(contorniArrayList)
        prodotti.addAll(dolciArrayList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnModifica.setOnClickListener() {

            val bundle = Bundle()
            bundle.putParcelableArrayList("bevande", bevandeArrayList)
            bundle.putParcelableArrayList("antipasti", antipastiArrayList)
            bundle.putParcelableArrayList("primi", primiArrayList)
            bundle.putParcelableArrayList("secondi", secondiArrayList)
            bundle.putParcelableArrayList("contorni", contorniArrayList)
            bundle.putParcelableArrayList("dolci", dolciArrayList)

            view?.findNavController()?.navigate(R.id.MenuToModifica)
        }
    }

    override fun onClickProduct(prodotto: Product) {

        val bundle = Bundle()
        bundle.putString("prodID", prodotto.idP.toString())
        bundle.putParcelableArrayList("prodArrayList", prodotti)

        //bundle.put

        view?.findNavController()?.navigate(R.id.productDetail, bundle)

    }

}