package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentRistorantiBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentRistoranti : Fragment(), RestaurantClickListener, RestaurantUtils {

    private lateinit var binding: FragmentRistorantiBinding
    private lateinit var adapter: RestaurantAdapter

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var resturantDataViewModel: RestaurantViewModel
    private lateinit var restArrayList: ArrayList<Restaurant>

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRistorantiBinding.inflate(layoutInflater)

        resturantDataViewModel =
            ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]
        resturantDataViewModel.arrayListRistorantiLiveData.observe(viewLifecycleOwner) {
            restArrayList = it
            val layoutManager = GridLayoutManager(context, 1)
            binding.recycleView.layoutManager = layoutManager
            adapter = RestaurantAdapter(restArrayList, this)
            binding.recycleView.adapter = adapter
            binding.recycleView.setHasFixedSize(true)
            showData()
            adapter.notifyDataSetChanged()

        }

        swipeRefreshLayout = binding.swipeRefreshRistoranti
        swipeRefreshLayout.setOnRefreshListener {
            getRestaurantData(object : FireBaseCallbackRestaurant {
                override fun onResponse(responseR: ResponseRistorante) {
                    restArrayList = responseR.ristoranti
                    val layoutManager = GridLayoutManager(context, 1)
                    binding.recycleView.layoutManager = layoutManager
                    adapter = RestaurantAdapter(restArrayList, this@FragmentRistoranti)
                    binding.recycleView.adapter = adapter
                    binding.recycleView.setHasFixedSize(true)
                    showData()
                    adapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                }
            }, context)
        }


        binding.checkpizza.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkburger.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkita.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkcin.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkgiap.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkind.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkgre.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)
        binding.checkveg.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.satoshi_regular)

        binding.searchBarRistoranti.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        binding.checkpizza.setOnClickListener {
            if (binding.checkpizza.isChecked) {
                adapter.customFilter().filter("Pizza")
            }
        }

        binding.checkburger.setOnClickListener {
            if (binding.checkburger.isChecked) {
                adapter.customFilter().filter("Burger")
            }
        }

        binding.checkita.setOnClickListener {
            if (binding.checkburger.isChecked) {
                adapter.customFilter().filter("Italiano")
            }
        }

        binding.checkcin.setOnClickListener {
            if (binding.checkcin.isChecked) {
                adapter.customFilter().filter("Cinese")
            }
        }

        binding.checkgiap.setOnClickListener {
            if (binding.checkgiap.isChecked) {
                adapter.customFilter().filter("Giapponese")
            }
        }

        binding.checkind.setOnClickListener {
            if (binding.checkind.isChecked) {
                adapter.customFilter().filter("Indiano")
            }
        }

        binding.checkgre.setOnClickListener {
            if (binding.checkgre.isChecked) {
                adapter.customFilter().filter("Greco")
            }
        }

        binding.checkveg.setOnClickListener {
            if (binding.checkveg.isChecked) {
                adapter.customFilter().filter("Vegan")
            }
        }

        return binding.root
    }

    override fun onClickResturant(restaurant: Restaurant) {

        val bundle = Bundle()
        bundle.putString("restID", restaurant.idR.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList)

        view?.findNavController()?.navigate(R.id.RistorantiToDetail, bundle)

    }

    private fun showData() {
        adapter.setData(restArrayList)
    }
}

