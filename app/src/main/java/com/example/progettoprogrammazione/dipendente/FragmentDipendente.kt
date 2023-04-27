package com.example.progettoprogrammazione.dipendente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.FragmentDipendenteBinding
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.utils.DipendenteUtils
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentDipendente : Fragment(), DipendenteUtils, UserUtils {

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

