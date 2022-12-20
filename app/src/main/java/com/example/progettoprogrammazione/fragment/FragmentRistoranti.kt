package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.example.progettoprogrammazione.activity.UserActivity
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


    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var resturantDataViewModel: RestaurantViewModel
    private lateinit var restArrayList: ArrayList<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRistorantiBinding.inflate(layoutInflater)

        /*
        val args = this.arguments
        restArrayList = args!!.getParcelableArrayList<Restaurant>("ristoranti") as ArrayList<Restaurant>

         */

        resturantDataViewModel=ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)
        resturantDataViewModel.arrayListaRistorantiLiveData.observe(viewLifecycleOwner){
                arraylist->
            val layoutManager = GridLayoutManager(context, 2)
            binding.recycleView.layoutManager = layoutManager
            adapter = RestaurantAdapter(arraylist, this)
            binding.recycleView.adapter = adapter
            binding.recycleView.setHasFixedSize(true)
            adapter.notifyDataSetChanged()


        }

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

        resturantDataViewModel=ViewModelProvider(requireActivity()).get(RestaurantViewModel::class.java)
        resturantDataViewModel.arrayListaRistorantiLiveData.observe(viewLifecycleOwner){
            ristorantiArrayList->


        }

    }


}

