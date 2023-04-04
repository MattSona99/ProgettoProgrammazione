package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.prodotti.ProductAdapter
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.example.progettoprogrammazione.utils.UserUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentMenu : Fragment(), ProductClickListener, ProductUtils, UserUtils, RestaurantUtils {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var adapter: ProductAdapter

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val cartViewModel : CartViewModel by navGraphViewModels(R.id.nav_restaurateur)

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
        val restaurantID = args?.get("idR")

        binding.btnModifica.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("bevande", bevandeArrayList)
            bundle.putParcelableArrayList("antipasti", antipastiArrayList)
            bundle.putParcelableArrayList("primi", primiArrayList)
            bundle.putParcelableArrayList("secondi", secondiArrayList)
            bundle.putParcelableArrayList("contorni", contorniArrayList)
            bundle.putParcelableArrayList("dolci", dolciArrayList)
            bundle.putString("idR", restaurantID.toString())

            view.findNavController().navigate(R.id.MenuToModifica, bundle)
        }

        binding.svuotamenu.setOnClickListener {
            binding.radioGroupMenu.clearCheck()
            invisible()
        }

        binding.btnBevande.setOnClickListener {
            invisible()
            bindrecyclerviews(bevandeArrayList, binding.recycleViewBevande)
            binding.bevandeMenu.isVisible = true
        }

        binding.btnAntipasti.setOnClickListener {
            invisible()
            bindrecyclerviews(antipastiArrayList, binding.recycleViewAntipasti)
            binding.antipastiMenu.isVisible = true
        }

        binding.btnPrimi.setOnClickListener {
            invisible()
            bindrecyclerviews(primiArrayList, binding.recycleViewPrimi)
            binding.primiMenu.isVisible = true
        }

        binding.btnSecondi.setOnClickListener {
            invisible()
            bindrecyclerviews(secondiArrayList, binding.recycleViewSecondi)
            binding.secondiMenu.isVisible = true
        }

        binding.btnContorni.setOnClickListener {
            invisible()
            bindrecyclerviews(contorniArrayList, binding.recycleViewContorni)
            binding.contorniMenu.isVisible = true
        }

        binding.btnDolci.setOnClickListener {
            invisible()
            bindrecyclerviews(dolciArrayList, binding.recycleViewDolci)
            binding.dolciMenu.isVisible = true
        }

    }


    override fun onClickProduct(prodotto: Product) {
        val bundle = Bundle()
        bundle.putString("prodID", prodotto.idP.toString())
        bundle.putParcelableArrayList("prodArrayList", prodotti)
        view?.findNavController()?.navigate(R.id.productDetail, bundle)
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

    private fun verticalrecylerview(
        recyclerView: RecyclerView,
        prodotti: ArrayList<Product>
    ) {
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        adapter = ProductAdapter(prodotti, this@FragmentMenu, requireContext(), cartViewModel)
        showData(prodotti)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
    }

    private fun bindrecyclerviews(
        prodotti: ArrayList<Product>,
        recyclerView: RecyclerView
    ) {
        verticalrecylerview(recyclerView, prodotti)
    }

    private fun showData(arrayList: ArrayList<Product>) {
        adapter.setData(arrayList)
    }
}