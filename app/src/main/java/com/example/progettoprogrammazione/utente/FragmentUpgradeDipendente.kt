package com.example.progettoprogrammazione.utente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentUpgrDipendenteBinding

class FragmentUpgradeDipendente : Fragment() {

    private lateinit var binding : FragmentUpgrDipendenteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgrDipendenteBinding.inflate(layoutInflater)
        return binding.root
    }
}