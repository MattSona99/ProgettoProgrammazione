package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.MainActivity
import com.example.progettoprogrammazione.decoration.RecyclerViewItemDecoration
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter


class FragmentRistoranti : Fragment() {

    private lateinit var adapter : RestaurantAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var restArrayList : ArrayList<Restaurant>

    lateinit var imageR : Array<Int>
    lateinit var nomeR : Array<String>
    lateinit var desc : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ristoranti, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = GridLayoutManager(context, 2)
//       val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = layoutManager
        adapter = RestaurantAdapter(restArrayList)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

    }

    private fun dataInitialize(){
        restArrayList = arrayListOf<Restaurant>()

        imageR= arrayOf(
            R.drawable.pencil,
            R.drawable.pencil,
            R.drawable.pencil
        )

        nomeR = arrayOf(
            "pino little italy",
            "poldo pizza",
            "la vecchia osteria"
        )

        desc = arrayOf(
            "desc1",
            "desc1",
            "desc1"
        )

        for (i in imageR.indices) {
            val restaurant = Restaurant (imageR[i], nomeR[i], desc[i])
            restArrayList.add(restaurant)
        }




    }

}