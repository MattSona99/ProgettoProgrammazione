package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentModificaMenuBinding
import com.example.progettoprogrammazione.databinding.ProductCardModificaEliminaBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.prodotti.ProductEMAdapter
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList


class FragmentModificaMenu : Fragment(), UserUtils, ProductUtils {

    private lateinit var binding: FragmentModificaMenuBinding
    private lateinit var adapter: ProductEMAdapter

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
        val restID = args?.get("idR") as String

        binding.btnBevanda.setOnClickListener {
            showDialog("bevanda", restID)
        }

        binding.btnAntipasto.setOnClickListener {
            showDialog("antipasto", restID)
        }

        binding.btnPrimo.setOnClickListener {
            showDialog("primo", restID)
        }

        binding.btnSecondo.setOnClickListener {
            showDialog("secondo", restID)
        }

        binding.btnContorno.setOnClickListener {
            showDialog("contorno", restID)
        }

        binding.btnDolce.setOnClickListener {
            showDialog("dolce", restID)
        }


        bevandeArrayList = args.getParcelableArrayList<Product>("bevande") as ArrayList<Product>
        antipastiArrayList = args.getParcelableArrayList<Product>("antipasti") as ArrayList<Product>
        primiArrayList = args.getParcelableArrayList<Product>("primi") as ArrayList<Product>
        secondiArrayList = args.getParcelableArrayList<Product>("secondi") as ArrayList<Product>
        contorniArrayList = args.getParcelableArrayList<Product>("contorni") as ArrayList<Product>
        dolciArrayList = args.getParcelableArrayList<Product>("dolci") as ArrayList<Product>



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

        val args = this.arguments
        val restaurantID = args?.get("idR") as String

        binding.btnBevande.setOnClickListener {
            invisible()
            bindrecyclerviews(bevandeArrayList,  binding.recycleViewBevande, restaurantID, "Bevande")
            binding.bevandeMenu.isVisible = true
        }

        binding.btnAntipasti.setOnClickListener {
            invisible()
            bindrecyclerviews(antipastiArrayList, binding.recycleViewAntipasti, restaurantID,"Antipasti")
            binding.antipastiMenu.isVisible = true
        }

        binding.btnPrimi.setOnClickListener {
            invisible()
            bindrecyclerviews(primiArrayList, binding.recycleViewPrimi, restaurantID,"Primi")
            binding.primiMenu.isVisible = true
        }

        binding.btnSecondi.setOnClickListener {
            invisible()
            bindrecyclerviews(secondiArrayList, binding.recycleViewSecondi, restaurantID,"Secondi")
            binding.secondiMenu.isVisible = true
        }

        binding.btnContorni.setOnClickListener {
            invisible()
            bindrecyclerviews(contorniArrayList, binding.recycleViewContorni, restaurantID,"Contorni")
            binding.contorniMenu.isVisible = true
        }

        binding.btnDolci.setOnClickListener {
            invisible()
            bindrecyclerviews(dolciArrayList, binding.recycleViewDolci, restaurantID,"Dolci")
            binding.dolciMenu.isVisible = true
        }

    }

    private fun invisible() {
        binding.tutteMenu.isGone = false
        binding.bevandeMenu.isGone = true
        binding.antipastiMenu.isGone = true
        binding.primiMenu.isGone = true
        binding.secondiMenu.isGone = true
        binding.contorniMenu.isGone = true
        binding.dolciMenu.isGone = true
    }

    private fun bindrecyclerviews(
        prodotti: ArrayList<Product>,
        recyclerView: RecyclerView,
        restID: String,
        tipo: String
    ) {
        val layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = ProductEMAdapter(prodotti, restID, tipo, requireContext())
        showData(prodotti)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
    }

    private fun showData(arrayList: ArrayList<Product>) {
        adapter.setData(arrayList)
    }

    private fun showDialog(add: String, restName: String?) {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.fragment_r_add_to_menu, null)
        val addDialog = AlertDialog.Builder(context)
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        val descrizioneP = v.findViewById<EditText>(R.id.descrizione_prodotto)

        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { _, _ ->
            val nomePbind = nomeP.text.toString()
            val prezzoPbind = prezzoP.text.toString()
            val descrizionePbind = descrizioneP.text.toString()
            val pData = Product(
                nomePbind,
                prezzoPbind,
                descrizionePbind,
                UUID.randomUUID().toString()
            )
            when (add) {
                "bevanda" -> addProdotto(restName, pData, "Bevande", context)
                "antipasto" -> addProdotto(restName, pData, "Antipasti", context)
                "primo" -> addProdotto(restName, pData, "Primi", context)
                "secondo" -> addProdotto(restName, pData, "Secondi", context)
                "contorno" -> addProdotto(restName, pData, "Contorni", context)
                "dolce" -> addProdotto(restName, pData, "Dolci",  context)
            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
    }

}