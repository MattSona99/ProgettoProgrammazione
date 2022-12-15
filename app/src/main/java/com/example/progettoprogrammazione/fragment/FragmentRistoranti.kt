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
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener


class FragmentRistoranti : Fragment() ,RestaurantClickListener{

    private lateinit var adapter: RestaurantAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var restArrayList: ArrayList<Restaurant>


    lateinit var imageR: Array<Int>
    lateinit var nomeR: Array<String>
    lateinit var desc: Array<String>


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
        dataInitialize()
        val layoutManager = GridLayoutManager(context, 2)
//       val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = layoutManager
        adapter = RestaurantAdapter(restArrayList,this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

    }

    private fun dataInitialize() {
        restArrayList = arrayListOf<Restaurant>()

        imageR = arrayOf(
            R.drawable.pencil,
            R.drawable.pencil,
            R.drawable.pencil,
            R.drawable.pencil,
            R.drawable.pencil,
            R.drawable.pencil,
            R.drawable.pencil
        )

        nomeR = arrayOf(
            "pino little italy",
            "poldo pizza",
            "la vecchia osteria",
            "pino little italy",
            "poldo pizza",
            "la vecchia osteria",
            "pino little italy"

        )

        desc = arrayOf(
            "desc1",
            "desc1",
            "desc1",
            "desc1",
            "desc1",
            "desc1",
            "desc1"
        )

        for (i in imageR.indices) {
            val restaurant = Restaurant(imageR[i], nomeR[i], desc[i], i)
            restArrayList.add(restaurant)
        }


    }

}

