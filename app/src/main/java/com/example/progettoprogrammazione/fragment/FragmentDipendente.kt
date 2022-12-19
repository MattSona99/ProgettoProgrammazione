package com.example.progettoprogrammazione.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentDipendenteBinding
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.dipendente.DipendenteAdapter
import com.example.progettoprogrammazione.dipendente.DipendenteClickListener
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.utils.DipendenteUtil
import com.example.progettoprogrammazione.utils.FireBaseCallbackDipendente
import com.example.progettoprogrammazione.utils.ResponseDipendente
import com.example.progettoprogrammazione.utils.UserUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentDipendente: Fragment(), DipendenteClickListener,DipendenteUtil,UserUtil {

    private lateinit var binding: FragmentDipendenteBinding

    private lateinit var adapter: DipendenteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dipArrayList: ArrayList<Dipendente>

    //private lateinit var dipendente: FragmentDipendente
    //private lateinit var user: User

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dipendente, container, false)
    }

    override fun onClickDipendente(dipendente: Dipendente) {

        val bundle = Bundle()
        bundle.putString("dipID", dipendente.id.toString())
        bundle.putParcelableArrayList("dipArrayList", dipArrayList as ArrayList<out Parcelable?>?)

        //view?.findNavController()?.navigate(R.id.DipendentiToDetail, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dipArrayList = arrayListOf()
        getDipendenteData(object : FireBaseCallbackDipendente {
            override fun onResponse(response: ResponseDipendente) {
                dipArrayList = response.dipendenti
            }
        }, context)

        val layoutManager = GridLayoutManager(context, 1)
        recyclerView = view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = layoutManager
        adapter = DipendenteAdapter(dipArrayList, this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

        }


}

