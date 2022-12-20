package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentRistorantiBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentRistoranti : Fragment(), RestaurantClickListener, RestaurantUtils {

    private lateinit var binding : FragmentRistorantiBinding
    private lateinit var adapter: RestaurantAdapter
    private lateinit var restArrayList: ArrayList<Restaurant>

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRistorantiBinding.inflate(layoutInflater)

        val args = arguments
        restArrayList = args!!.getParcelableArrayList<Restaurant>("ristoranti") as ArrayList<Restaurant>

        val layoutManager = GridLayoutManager(context, 2)
        binding.recycleView.layoutManager = layoutManager
        adapter = RestaurantAdapter(restArrayList, this)
        binding.recycleView.adapter = adapter
        binding.recycleView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onClickResturant(restaurant: Restaurant) {

        val bundle = Bundle()
        bundle.putString("restID", restaurant.id.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList as ArrayList<out Parcelable?>?)

        view?.findNavController()?.navigate(R.id.RistorantiToDetail, bundle)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}

