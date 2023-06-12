package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.Fragment1SearchBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.FiltriUtils
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questo fragment permette all'utente di cercare un ristorante specifico all'interno del database

class FragmentSearch : Fragment(), RestaurantClickListener, RestaurantUtils, FiltriUtils {

    private lateinit var binding: Fragment1SearchBinding
    private lateinit var adapter: RestaurantAdapter

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var restArrayList: ArrayList<Restaurant>
    private lateinit var lvl : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1SearchBinding.inflate(layoutInflater)

        val args = this.arguments
        lvl = args?.getString("lvl") as String

        restArrayList = arrayListOf()

        // Filtra i ristoranti a seconda del testo inserito all'interno della Search Bar
        binding.searchBarRistoranti.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getRestaurantData(object : FireBaseCallbackRestaurant {
                    override fun onResponse(responseR: ResponseRistorante) {
                        restArrayList = responseR.ristoranti
                        val layoutManager = GridLayoutManager(context, 2)
                        binding.recycleViewSearch.layoutManager = layoutManager
                        adapter = RestaurantAdapter(searchFilter(restArrayList, newText), this@FragmentSearch)
                        binding.recycleViewSearch.adapter = adapter
                        binding.recycleViewSearch.setHasFixedSize(true)
                        adapter.notifyDataSetChanged()
                    }
                }, context)
                return false
            }
        })

        return binding.root
    }

    //     // Quando viene effettuato un click su un ristorante, si effettuerà la navigazione verso i suoi dettagli
    override fun onClickResturant(restaurant: Restaurant) {
        binding.searchBarRistoranti.setQuery("", false)
        val bundle = Bundle()
        bundle.putString("restID", restaurant.idR.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList)
        when(lvl){
            "1" -> view?.findNavController()?.navigate(R.id.SearchToDetailU, bundle)
            "2" -> view?.findNavController()?.navigate(R.id.SearchToDetailD, bundle)
            "3" -> view?.findNavController()?.navigate(R.id.SearchToDetailR, bundle)
        }

    }
}