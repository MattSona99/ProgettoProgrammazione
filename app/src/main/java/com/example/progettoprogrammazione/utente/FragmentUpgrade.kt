package com.example.progettoprogrammazione.utente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentUpgrProprietarioBinding
import com.example.progettoprogrammazione.databinding.FragmentUpgradeBinding

class FragmentUpgrade : Fragment() {

    private lateinit var binding: FragmentUpgradeBinding
    private lateinit var bindingUpgrProp: FragmentUpgrProprietarioBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpgradeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProprietario.setOnClickListener(){
            parentFragmentManager.beginTransaction().replace(R.id.form_container, bindingUpgrProp as Fragment )
        }


    }

    }
