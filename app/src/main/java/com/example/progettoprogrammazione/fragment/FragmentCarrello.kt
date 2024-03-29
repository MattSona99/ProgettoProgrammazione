package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.carrello.CartAdapter
import com.example.progettoprogrammazione.databinding.Fragment1CarrelloBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.CartUtils
import com.example.progettoprogrammazione.utils.calcolatotale_class
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dalvik.annotation.TestTargetClass
import org.jetbrains.annotations.TestOnly


// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
/* Questo fragment mostra il carrello temporaneo (se sono stati aggiunti dei prodotti), mostrando
il prezzo totale dell'ordine e permettendo di salvarlo nel database */


class FragmentCarrello : Fragment(), CartUtils, ProductUtils {

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: Fragment1CarrelloBinding
    private lateinit var user: User

    private lateinit var cartViewModel: CartViewModel
    private val cartViewModelR: CartViewModel by navGraphViewModels(R.id.nav_restaurateur)
    private val cartViewModelU: CartViewModel by navGraphViewModels(R.id.nav_user)
    private val cartViewModelD: CartViewModel by navGraphViewModels(R.id.nav_dipendente)

    private var cartProduct = arrayListOf<CartProduct>()
    private lateinit var adapter: CartAdapter

    // Crea un instanza della classe calcolatotale
    private val calcolatotale_classInstance = calcolatotale_class()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1CarrelloBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User
        when (user.Livello) {
            "1" -> cartViewModel = cartViewModelU
            "2" -> cartViewModel = cartViewModelD
            "3" -> cartViewModel = cartViewModelR
        }

        // Eventuali controlli per permettere alla grafica di mostrare determinati elementi
        if (cartViewModel.getcartItems().value!!.isEmpty()) {
            binding.totCarrelloLayout.isGone = true
            binding.noProduct.isVisible = true
            binding.constraintQR.isGone = true
        } else {
            binding.totCarrelloLayout.isVisible = true
            binding.noProduct.isGone = true
            binding.constraintQR.isVisible = true
        }

        // Aggiunge alla recyclerview i prodotto salvati dentro alla lista del viewmodel del carrello
        cartViewModel.getcartItems().observe(viewLifecycleOwner) { cartItems ->

            var totale: Float? = calcolatotale_classInstance.calcolaTotale(cartItems)

            binding.totaleCarrello.text = totale.toString() + " €"
            val layoutManager = GridLayoutManager(context, 2)
            binding.recylerOrder.layoutManager = layoutManager
            adapter = CartAdapter(cartItems, cartViewModel, binding, requireContext())
            adapter.setData(cartItems)
            binding.recylerOrder.adapter = adapter
            binding.recylerOrder.setHasFixedSize(true)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    // Elimina l'icona del carrello dal menu quando ci troviamo sul questo fragment
    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.ic_cart)
        if (menuItem != null) {
            menuItem.isVisible = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Effettua il salvataggio del carrello nel database richiamando la funzione apposita,
        svuotando il viewmodel temporaneo */
        cartViewModel.getcartItems().observe(viewLifecycleOwner) { cartItems ->
            cartProduct.clear()

            var totale: Float? = calcolatotale_classInstance.calcolaTotale(cartItems)

            binding.constraintQR.setOnClickListener {
                addQRData(cartProduct, firebaseAuth.uid, context)
                cartViewModel.deleteCartItems()
                when (user.Livello) {
                    "1" -> view.findNavController().navigate(R.id.CarrelloToQR_U)
                    "2" -> view.findNavController().navigate(R.id.CarrelloToQR_D)
                    "3" -> view.findNavController().navigate(R.id.CarrelloToQR_R)
                }
            }
        }

    }
}
