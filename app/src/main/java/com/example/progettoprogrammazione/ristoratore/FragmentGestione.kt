package com.example.progettoprogrammazione.ristoratore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentGestioneRistBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.FiltriUtils
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentGestione : Fragment(), RestaurantClickListener, RestaurantUtils, FiltriUtils {

    private lateinit var binding: FragmentGestioneRistBinding
    private lateinit var adapter: RestaurantAdapter

    private lateinit var user: User
    private lateinit var userlvl: String

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var restArrayListR: ArrayList<Restaurant>
    private lateinit var restArrayList: ArrayList<Restaurant>

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGestioneRistBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User
        userlvl = args.get("userlvl") as String

        restArrayListR = arrayListOf()
        restArrayList = arrayListOf()

        getRestaurantData(object : FireBaseCallbackRestaurant {
            override fun onResponse(responseR: ResponseRistorante) {
                restArrayList = responseR.ristoranti
                for (restaurant in restArrayList) {
                    if (restaurant.proprietarioR == user.Email) restArrayListR.add(restaurant)
                }
                val layoutManager = GridLayoutManager(context, 2)
                binding.recycleViewRist.layoutManager = layoutManager
                adapter = RestaurantAdapter(restArrayListR, this@FragmentGestione)
                binding.recycleViewRist.adapter = adapter
                binding.recycleViewRist.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }
        }, context)


        swipeRefreshLayout = binding.swipeRefreshGestione
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            restArrayListR.clear()
            binding.searchBarGestione.setQuery("", false)
            getRestaurantData(object : FireBaseCallbackRestaurant {
                override fun onResponse(responseR: ResponseRistorante) {
                    restArrayList = responseR.ristoranti
                    for (restaurant in restArrayList) {
                        if (restaurant.proprietarioR == user.Email) restArrayListR.add(restaurant)
                    }
                    val layoutManager = GridLayoutManager(context, 2)
                    binding.recycleViewRist.layoutManager = layoutManager
                    adapter = RestaurantAdapter(restArrayListR, this@FragmentGestione)
                    binding.recycleViewRist.adapter = adapter
                    binding.recycleViewRist.setHasFixedSize(true)
                    adapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                }
            }, context)
        }

        binding.searchBarGestione.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val layoutManager = GridLayoutManager(context, 2)
                binding.recycleViewRist.layoutManager = layoutManager
                adapter =
                    RestaurantAdapter(searchFilter(restArrayList, newText), this@FragmentGestione)
                binding.recycleViewRist.adapter = adapter
                binding.recycleViewRist.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
                return false
            }

        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCrea.setOnClickListener {
            val bundleu = Bundle()
            bundleu.putParcelable("user", user)
            view.findNavController().navigate(R.id.GestioneToCrea, bundleu)
        }
    }

    override fun onClickResturant(restaurant: Restaurant) {

        val bundle = Bundle()
        bundle.putString("restID", restaurant.idR.toString())
        bundle.putParcelable("user", user)
        bundle.putParcelableArrayList("restArrayList", restArrayList)
        bundle.putString("userlvl", userlvl)

        view?.findNavController()?.navigate(R.id.GestioneToDetail, bundle)

    }

}