package com.example.progettoprogrammazione.ristoratore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentModificaMenuBinding
import com.example.progettoprogrammazione.databinding.ProductCardModificaEliminaBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.prodotti.ProductEMAdapter
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentModificaMenu : Fragment(), ProductClickListener, UserUtils {

    private lateinit var binding: FragmentModificaMenuBinding
    private lateinit var adapterBev: ProductEMAdapter
    private lateinit var adapterAnt: ProductEMAdapter
    private lateinit var adapterPri: ProductEMAdapter
    private lateinit var adapterSec: ProductEMAdapter
    private lateinit var adapterCon: ProductEMAdapter
    private lateinit var adapterDol: ProductEMAdapter

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var bevandeArrayList: ArrayList<Product>
    private lateinit var antipastiArrayList: ArrayList<Product>
    private lateinit var primiArrayList: ArrayList<Product>
    private lateinit var secondiArrayList: ArrayList<Product>
    private lateinit var contorniArrayList: ArrayList<Product>
    private lateinit var dolciArrayList: ArrayList<Product>

    private var prodotti = arrayListOf<Product>()

    private lateinit var prodottoBinding: ProductCardModificaEliminaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModificaMenuBinding.inflate(layoutInflater)

        prodottoBinding = ProductCardModificaEliminaBinding.inflate(layoutInflater)

        val args = this.arguments
        val restID = args?.get("idR")

        bevandeArrayList = args?.getParcelableArrayList<Product>("bevande") as ArrayList<Product>
        antipastiArrayList = args.getParcelableArrayList<Product>("antipasti") as ArrayList<Product>
        primiArrayList = args.getParcelableArrayList<Product>("primi") as ArrayList<Product>
        secondiArrayList = args.getParcelableArrayList<Product>("secondi") as ArrayList<Product>
        contorniArrayList = args.getParcelableArrayList<Product>("contorni") as ArrayList<Product>
        dolciArrayList = args.getParcelableArrayList<Product>("dolci") as ArrayList<Product>

        val layoutManagerBev = GridLayoutManager(context, 2)
        binding.recycleViewBevande.layoutManager = layoutManagerBev
        adapterBev = ProductEMAdapter(bevandeArrayList, this, restID.toString(), "Bevande", requireContext())
        binding.recycleViewBevande.adapter = adapterBev
        binding.recycleViewBevande.setHasFixedSize(true)

        val layoutManagerAnt = GridLayoutManager(context, 2)
        binding.recycleViewAntipasti.layoutManager = layoutManagerAnt
        adapterAnt = ProductEMAdapter(antipastiArrayList, this, restID.toString(), "Antipasti", requireContext())
        binding.recycleViewAntipasti.adapter = adapterAnt
        binding.recycleViewAntipasti.setHasFixedSize(true)

        val layoutManagerPri = GridLayoutManager(context, 2)
        binding.recycleViewPrimi.layoutManager = layoutManagerPri
        adapterPri = ProductEMAdapter(primiArrayList, this, restID.toString(), "Primi", requireContext())
        binding.recycleViewPrimi.adapter = adapterPri
        binding.recycleViewPrimi.setHasFixedSize(true)

        val layoutManagerSec = GridLayoutManager(context, 2)
        binding.recycleViewSecondi.layoutManager = layoutManagerSec
        adapterSec = ProductEMAdapter(secondiArrayList, this, restID.toString(), "Secondi", requireContext())
        binding.recycleViewSecondi.adapter = adapterSec
        binding.recycleViewSecondi.setHasFixedSize(true)

        val layoutManagerCon = GridLayoutManager(context, 2)
        binding.recycleViewContorni.layoutManager = layoutManagerCon
        adapterCon = ProductEMAdapter(contorniArrayList, this, restID.toString(), "Contorni", requireContext())
        binding.recycleViewContorni.adapter = adapterCon
        binding.recycleViewContorni.setHasFixedSize(true)

        val layoutManagerDol = GridLayoutManager(context, 2)
        binding.recycleViewDolci.layoutManager = layoutManagerDol
        adapterDol = ProductEMAdapter(dolciArrayList, this, restID.toString(), "Dolci", requireContext())
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

        prodottoBinding.btnElimina.setOnClickListener {

        }

        prodottoBinding.btnModifica.setOnClickListener {

        }
    }

    override fun onClickProduct(prodotto: Product) {

        val bundle = Bundle()
        bundle.putString("prodID", prodotto.idP.toString())
        bundle.putParcelableArrayList("prodArrayList", prodotti)

        view?.findNavController()?.navigate(R.id.productDetail, bundle)

    }

}