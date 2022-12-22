package com.example.progettoprogrammazione.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.EmployeeActivity
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.activity.UserActivity
import com.example.progettoprogrammazione.databinding.FragmentLoginBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentLogin : Fragment(), UserUtil, DipendenteUtil, RestaurantUtils {

    private lateinit var binding: FragmentLoginBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var userlvl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ConstraintLogin.setOnClickListener() {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        //FUNZIONE CHE PRENDE DATI RISTORANTI

                        /*
                        FUNZIONE CHE PRENDE I DATI DI DIPENDENTE

                        dipArrayList = arrayListOf()
                        getDipendenteData(object : FireBaseCallbackDipendente {
                            override fun onResponse(response: ResponseDipendente) {
                                dipArrayList = response.dipendenti
                            }
                        }, context)
                        */

                        //LOGIN
                        getUserData(object : FireBaseCallbackUser {
                            override fun onResponse(responseU: ResponseUser) {
                                userlvl = responseU.user!!.Livello

                                Toast.makeText(
                                    context,
                                    "Login effettuato con successo!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                getRestaurantData(object : FireBaseCallbackRestaurant {
                                    override fun onResponse(responseR: ResponseRistorante) {

                                        when (userlvl) {
                                            "1" -> {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        UserActivity::class.java
                                                    ).apply {
                                                        putExtra("user", responseU.user)
                                                        putParcelableArrayListExtra(
                                                            "ristoranti",
                                                            responseR.ristoranti
                                                        )
                                                    }
                                                startActivity(intent)
                                                activity?.finish()
                                            }

                                            "2" -> {

                                                val intent =
                                                    Intent(
                                                        context,
                                                        EmployeeActivity::class.java
                                                    ).apply {
                                                        putExtra("user", responseU.user)
                                                        putParcelableArrayListExtra(
                                                            "ristoranti",
                                                            responseR.ristoranti
                                                        )
                                                    }
                                                startActivity(intent)
                                                activity?.finish()
                                            }

                                            "3" -> {

                                                val intent =
                                                    Intent(
                                                        context,
                                                        RestaurateurActivity::class.java
                                                    ).apply {
                                                        putExtra("user", responseU.user)
                                                        putParcelableArrayListExtra(
                                                            "ristoranti",
                                                            responseR.ristoranti
                                                        )
                                                    }
                                                startActivity(intent)
                                                activity?.finish()

                                            }
                                            else -> {
                                                Toast.makeText(
                                                    context,
                                                    "Errore durante il caricamento.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    }
                                }, context)
                            }
                        }, context)

                    } else Toast.makeText(
                        context,
                        "Email e password non corrispondono!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(context, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.noaccount.setOnClickListener {
            view.findNavController().navigate(R.id.LoginToRegister)
        }

    }
}
