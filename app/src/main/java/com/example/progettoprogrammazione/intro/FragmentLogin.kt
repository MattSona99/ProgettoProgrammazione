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
import com.example.progettoprogrammazione.databinding.Fragment0LoginBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackCart
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentLogin : Fragment(), UserUtils, DipendenteUtils, RestaurantUtils, ProductUtils,
    CartUtils {

    private lateinit var binding: Fragment0LoginBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var userlvl: String? = null
    private lateinit var useremail: String

    fun login(email:String, password:String){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

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

                            getQRData(
                                FirebaseAuth.getInstance().uid,
                                object : FireBaseCallbackCart {
                                    override fun onResponse(responseC: ResponseCart) {
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
                                                                putExtra(
                                                                    "cart",
                                                                    responseC.cart
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
                                                                putExtra(
                                                                    "cart",
                                                                    responseC.cart
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
                                                                putExtra(
                                                                    "cart",
                                                                    responseC.cart
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
                                },
                                context
                            )
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment0LoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ConstraintLogin.setOnClickListener {
            login( binding.email.text.toString(), binding.password.text.toString())
        }

        binding.recuperapassword.setOnClickListener {
            useremail = binding.email.text.toString()
            if (useremail.isNotEmpty()) {
                recoverUserPassword(context, useremail)
            } else Toast.makeText(context, "Inserisci un'email.", Toast.LENGTH_SHORT).show()

        }

        binding.noaccount.setOnClickListener {
            view.findNavController().navigate(R.id.LoginToRegister)
        }

    }
}
