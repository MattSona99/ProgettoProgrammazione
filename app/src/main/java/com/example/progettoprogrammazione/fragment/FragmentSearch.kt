package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentSearchBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.FiltriUtils
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentSearch : Fragment(), RestaurantClickListener, RestaurantUtils, FiltriUtils {

    private lateinit var binding: FragmentSearchBinding
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
        binding = FragmentSearchBinding.inflate(layoutInflater)

        val args = this.arguments
        lvl = args?.getString("lvl") as String

        restArrayList = arrayListOf()

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