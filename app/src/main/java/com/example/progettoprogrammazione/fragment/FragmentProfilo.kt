package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.models.User


class FragmentProfilo : Fragment() {

    private lateinit var binding: FragmentProfiloBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfiloBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val nicknameu = view.findViewById<TextView>(R.id.nicknameprofilo)
        val nomeu = view.findViewById<EditText>(R.id.nomeprofilo)
        val cognomeu = view.findViewById<EditText>(R.id.cognomeprofilo)
        val passwordu = view.findViewById<EditText>(R.id.passwordprofilo)

        val args = this.arguments
        val user = args?.getParcelable<User>("user") as User

        nicknameu?.text = user.Nome + " " + user.Cognome
        nomeu?.hint = "Nome: " + user.Nome
        cognomeu?.hint = "Cognome: " + user.Cognome
        passwordu?.hint = "Password: " + user.Password

        binding.creaRistorante.setOnClickListener() {
            view.findNavController().navigate(R.id.ProfiloToUpgrade)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}

