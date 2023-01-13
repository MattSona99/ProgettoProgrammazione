package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentSearchBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentSearch : Fragment(), RestaurantClickListener, RestaurantUtils {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: RestaurantAdapter

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var restArrayList: ArrayList<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        restArrayList = arrayListOf()

        getRestaurantData(object : FireBaseCallbackRestaurant {
            override fun onResponse(responseR: ResponseRistorante) {
                restArrayList = responseR.ristoranti
                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.recycleViewSearch.layoutManager = layoutManager
                adapter = RestaurantAdapter(restArrayList, this@FragmentSearch)
                binding.recycleViewSearch.adapter = adapter
                binding.recycleViewSearch.setHasFixedSize(true)
                showData()
                binding.recycleViewSearch.isVisible = false
                adapter.notifyDataSetChanged()
            }
        }, context)

        binding.searchBarRistoranti.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                if(newText!!.isEmpty()){
                    binding.recycleViewSearch.isVisible = false
                } else  binding.recycleViewSearch.isVisible = true
                return false
            }
        })

        return binding.root
    }

    override fun onClickResturant(restaurant: Restaurant) {
        val bundle = Bundle()
        bundle.putString("restID", restaurant.idR.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList)

        view?.findNavController()?.navigate(R.id.SearchToDetailR, bundle)
    }

    private fun showData() {
        adapter.setData(restArrayList)
    }
}