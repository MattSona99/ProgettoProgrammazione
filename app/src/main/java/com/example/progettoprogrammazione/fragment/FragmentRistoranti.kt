package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentRistoranti : Fragment(), RestaurantClickListener, RestaurantUtils, UserUtils {

    private lateinit var binding: FragmentRistorantiBinding
    private lateinit var adapter: RestaurantAdapter

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

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
                horizontalrecylerview(restArrayList, "rating", binding.recycleViewTopRated)
            }
        }, context)

        binding.scrollviewrist.viewTreeObserver.addOnScrollChangedListener {
            swipeRefreshLayout.isEnabled = binding.scrollviewrist.scrollY == 0
        }

        swipeRefreshLayout = binding.swipeRefreshRistoranti
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true

            getRestaurantData(object : FireBaseCallbackRestaurant {
                override fun onResponse(responseR: ResponseRistorante) {
                    restArrayList = responseR.ristoranti
                    horizontalrecylerview(restArrayList, "rating", binding.recycleViewTopRated)
                    binding.tutte.isGone = true
                    binding.radioGroup.clearCheck()
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
        ristoranti: ArrayList<Restaurant>,
        tipo: String,
        recyclerView: RecyclerView
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        adapter = RestaurantAdapter(ristoranti, this@FragmentRistoranti)
        adapter.customFilter().filter(tipo)
        adapter.setData(restArrayList)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
    }

    private fun invisible() {
        binding.tutte.isGone = false
        binding.pizzerie.isGone = true
        binding.pub.isGone = true
        binding.italiana.isGone = true
        binding.cinese.isGone = true
        binding.giapponese.isGone = true
        binding.indiana.isGone = true
        binding.greco.isGone = true
        binding.vegano.isGone = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPizza.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "pizza", binding.recycleViewPizza)
            binding.pizzerie.isVisible = true
        }
        binding.btnBurger.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "burger", binding.recycleViewBurger)
            binding.pub.isVisible = true
        }
        binding.btnIta.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "italiano", binding.recycleViewIta)
            binding.italiana.isVisible = true
        }
        binding.btnChi.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "cinese", binding.recycleViewCin)
            binding.cinese.isVisible = true
        }
        binding.btnJap.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "giapponese", binding.recycleViewJap)
            binding.giapponese.isVisible = true
        }
        binding.btnInd.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "indiano", binding.recycleViewInd)
            binding.indiana.isVisible = true
        }
        binding.btnGre.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "greco", binding.recycleViewGre)
            binding.greco.isVisible = true
        }
        binding.btnVeg.setOnClickListener {
            invisible()
            horizontalrecylerview(restArrayList, "vegan", binding.recycleViewVeg)
            binding.vegano.isVisible = true
        }
    }
}

