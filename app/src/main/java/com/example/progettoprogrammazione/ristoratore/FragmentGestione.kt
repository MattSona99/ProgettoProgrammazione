package com.example.progettoprogrammazione.ristoratore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentGestioneRistBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentGestione : Fragment(), RestaurantClickListener, RestaurantUtils {

    private lateinit var binding: FragmentGestioneRistBinding
    private lateinit var adapter: RestaurantAdapter

    private lateinit var user : User


    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var resturantDataViewModel: RestaurantViewModel
    private lateinit var restArrayList: ArrayList<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGestioneRistBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        restArrayList = arrayListOf()

        resturantDataViewModel =
            ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]
        resturantDataViewModel.arrayListRistorantiLiveData.observe(viewLifecycleOwner) {
            for(restaurant : Restaurant in it) {
                if(restaurant.proprietarioR == user.Email) restArrayList.add(restaurant)
            }
            val layoutManager = GridLayoutManager(context, 1)
            binding.recycleViewRist.layoutManager = layoutManager
            adapter = RestaurantAdapter(restArrayList, this)
            binding.recycleViewRist.adapter = adapter
            binding.recycleViewRist.setHasFixedSize(true)
            adapter.notifyDataSetChanged()

        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCrea.setOnClickListener {
            view.findNavController().navigate(R.id.GestioneToCrea)
        }
    }

    override fun onClickResturant(restaurant: Restaurant) {

        val bundle = Bundle()
        bundle.putString("restID", restaurant.idR.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList)

        view?.findNavController()?.navigate(R.id.GestioneToDetail, bundle)

    }

}