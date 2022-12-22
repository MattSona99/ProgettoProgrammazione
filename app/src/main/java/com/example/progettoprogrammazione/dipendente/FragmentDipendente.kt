package com.example.progettoprogrammazione.dipendente

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentDipendenteBinding
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.databinding.FragmentRistorantiBinding
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
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentDipendente : Fragment(), DipendenteUtil, UserUtil {

    private lateinit var binding: FragmentDipendenteBinding

    private lateinit var adapter: DipendenteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dipArrayList: ArrayList<Dipendente>

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDipendenteBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}

