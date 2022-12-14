package com.example.progettoprogrammazione.utente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentUpgradeBinding

class FragmentUpgrade : Fragment() {

    private lateinit var binding: FragmentUpgradeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgradeBinding.inflate(layoutInflater)
        return binding.root
    }
}