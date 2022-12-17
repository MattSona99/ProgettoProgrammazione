package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.restaurantList
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.utils.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentRistoranti : Fragment() ,RestaurantClickListener, RestaurantUtils{

    private lateinit var adapter: RestaurantAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var restArrayList: ArrayList<Restaurant>

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ristoranti, container, false)
    }


    override fun onClickResturant(restaurant: Restaurant) {

        val bundle = Bundle()
        bundle.putString("restID", restaurant.id.toString())
        bundle.putParcelableArrayList("restArrayList", restArrayList as ArrayList<out Parcelable?>?)

/*        val fragmentDetail = RestaurantDetail()
        fragmentDetail.arguments = bundle
*/
        view?.findNavController()?.navigate(R.id.RistorantiToDetail, bundle)

/*
        val intent = Intent(context, RestaurantDetail::class.java)
        intent.putExtra(RESTAURANT_EXTRA, restaurant.id)
        startActivity(intent)
*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restArrayList = arrayListOf<Restaurant>()
        getRestaurantData(object : FireBaseCallbackRestaurant {
            override fun onResponse(response: ResponseRistorante) {

                restArrayList.add(response.ristorante!!)
            }

        },context)


        val layoutManager = GridLayoutManager(context, 2)
//      val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = layoutManager
        adapter = RestaurantAdapter(restArrayList,this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()




    }



}

