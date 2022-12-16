package com.example.progettoprogrammazione.utente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.activity.UserActivity
import com.example.progettoprogrammazione.databinding.FragmentUpgrProprietarioBinding
import com.example.progettoprogrammazione.utils.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentUpgradeProprietario : Fragment(), UserUtil {

    private lateinit var binding: FragmentUpgrProprietarioBinding

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgrProprietarioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Nome = binding.nomeristoranteNewR.text.toString()
        val descrizione = binding.descrizioneNewR.text.toString()
        val Indirizzo = binding.indirizzoNewR.text.toString()

        if (Nome.length > 20) {
            binding.nomeristoranteNewR.setError("Il nome non può essere lungo più di 20 caratteri.")
        }
        if (descrizione.length > 20) {
            binding.nomeristoranteNewR.setError("La descizione non può essere lungo più di 20 caratteri.")
        }
        if (Indirizzo.length > 20) {
            binding.nomeristoranteNewR.setError("L' Indirizzo non può essere lungo più di 20 caratteri.")
        }


        binding.ConstraintCreaRist.setOnClickListener {
            val childUpdates = hashMapOf<String, Any>(
                "Livello" to "3"
            )
            updateUserData(
                context, childUpdates
            )
            getUserData(object : FireBaseCallbackUser {
                override fun onResponse(response: ResponseUser) {
                    val intent = Intent(context, RestaurateurActivity::class.java).apply {
                        putExtra("user", response.user)
                    }
                    startActivity(intent)
                    activity?.finish()
                }
            }, context)
        }
    }

}
