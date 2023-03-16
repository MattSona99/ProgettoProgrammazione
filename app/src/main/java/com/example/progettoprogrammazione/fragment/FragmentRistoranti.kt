package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentRistorantiBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.example.progettoprogrammazione.utils.UserUtils
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentRistoranti : Fragment(), RestaurantClickListener, RestaurantUtils, UserUtils {

    private lateinit var binding: FragmentRistorantiBinding
    private lateinit var adapter: RestaurantAdapter

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var resturantDataViewModel: RestaurantViewModel
    private lateinit var restArrayList: ArrayList<Restaurant>
    private lateinit var userlvl: String

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRistorantiBinding.inflate(layoutInflater)

        restArrayList = arrayListOf()
        getUserData(object : FireBaseCallbackUser {
            override fun onResponse(responseU: ResponseUser) {
                userlvl = responseU.user!!.Livello.toString()
            }
        }, context)

        getRestaurantData(object : FireBaseCallbackRestaurant {
            override fun onResponse(responseR: ResponseRistorante) {
                restArrayList = responseR.ristoranti
                bindrecyclerviews(restArrayList, "rating", binding.recycleViewTopRated)
            }
        }, context)

        //NON TOCCARE
        binding.scrollviewrist.viewTreeObserver.addOnScrollChangedListener(object :
            ViewTreeObserver.OnScrollChangedListener {
            override fun onScrollChanged() {
                if (binding.scrollviewrist.scrollY == 0) {
                    swipeRefreshLayout.isEnabled = true
                } else swipeRefreshLayout.isEnabled = false
            }
        })

        swipeRefreshLayout = binding.swipeRefreshRistoranti
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true

            getRestaurantData(object : FireBaseCallbackRestaurant {
                override fun onResponse(responseR: ResponseRistorante) {
                    restArrayList = responseR.ristoranti
                    bindrecyclerviews(restArrayList, "rating", binding.recycleViewTopRated)
                }
            }, context)
            swipeRefreshLayout.isRefreshing = false
        }

        binding.searchBarLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val bundle = Bundle()
                bundle.putString("lvl", userlvl)
                when (userlvl) {
                    "1" -> view?.findNavController()?.navigate(R.id.RistorantiToSearchU, bundle)
                    "2" -> view?.findNavController()?.navigate(R.id.RistorantiToSearchD, bundle)
                    "3" -> view?.findNavController()?.navigate(R.id.RistorantiToSearchR, bundle)
                }
            }
        })

        return binding.root
    }

    override fun onClickResturant(restaurant: Restaurant) {
        val bundle = Bundle()
        bundle.putString("restID", restaurant.idR.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList)

        view?.findNavController()?.navigate(R.id.RistorantiToDetail, bundle)

    }

    private fun horizontalrecylerview(
        recyclerView: RecyclerView,
        ristoranti: ArrayList<Restaurant>,
        tipo: String
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = RestaurantAdapter(ristoranti, this@FragmentRistoranti)
        showData()
        customFilter(adapter, tipo)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
    }

    private fun verticalrecylerview(
        recyclerView: RecyclerView,
        ristoranti: ArrayList<Restaurant>,
        tipo: String
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = RestaurantAdapter(ristoranti, this@FragmentRistoranti)
        showData()
        customFilter(adapter, tipo)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
    }

    private fun customFilter(adapter: RestaurantAdapter, tipo: String) {
        adapter.customFilter().filter(tipo)
    }

    private fun bindrecyclerviews(
        ristoranti: ArrayList<Restaurant>,
        tipo: String,
        recyclerView: RecyclerView
    ) {
        horizontalrecylerview(recyclerView, ristoranti, tipo)
    }

    private fun showData() {
        adapter.setData(restArrayList)
    }

    private fun invisible() {
        binding.pizzerie.isVisible = false
        binding.pub.isVisible = false
        binding.italiana.isVisible = false
        binding.cinese.isVisible = false
        binding.giapponese.isVisible = false
        binding.indiana.isVisible = false
        binding.greco.isVisible = false
        binding.vegano.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPizza.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "pizza", binding.recycleViewPizza)
            binding.pizzerie.isVisible = true
        }
        binding.btnBurger.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "burger", binding.recycleViewBurger)
            binding.pub.isVisible = true
        }
        binding.btnIta.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "italiano", binding.recycleViewIta)
            binding.italiana.isVisible = true
        }
        binding.btnChi.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "cinese", binding.recycleViewCin)
            binding.cinese.isVisible = true
        }
        binding.btnJap.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "giapponese", binding.recycleViewJap)
            binding.giapponese.isVisible = true
        }
        binding.btnInd.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "indiano", binding.recycleViewInd)
            binding.indiana.isVisible = true
        }
        binding.btnGre.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "greco", binding.recycleViewGre)
            binding.greco.isVisible = true
        }
        binding.btnVeg.setOnClickListener {
            invisible()
            bindrecyclerviews(restArrayList, "pizza", binding.recycleViewVeg)
            binding.vegano.isVisible = true
        }
    }
}

