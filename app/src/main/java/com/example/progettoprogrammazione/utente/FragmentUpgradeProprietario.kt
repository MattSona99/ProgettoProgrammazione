package com.example.progettoprogrammazione.utente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentUpgrProprietarioBinding

class FragmentUpgradeProprietario : Fragment() {

    private lateinit var binding : FragmentUpgrProprietarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgrProprietarioBinding.inflate(layoutInflater)
        return binding.root
    }
}