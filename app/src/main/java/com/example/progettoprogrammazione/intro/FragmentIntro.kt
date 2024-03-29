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
import com.example.progettoprogrammazione.databinding.Fragment0IntroBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackCart
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
/* Per chi accede all'applicazione, questo sarà il fragment iniziale;
 Chi utilizza questo fragment ha accesso alla pagina di Login o Registrati, oppure accede
 direttamente all'interno dell'applicazione se è già stato effettuato almeno una volta l'accesso */


class FragmentIntro : Fragment(), UserUtils, RestaurantUtils, ProductUtils, CartUtils {

    private lateinit var binding: Fragment0IntroBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    var userlvl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment0IntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Questa funzione effettua il login automatico e, controllando il livello utente,
        // accede ad una determinata Activity;
        // Se il login non è stato effettuato almeno una volta in passato, la navigazione andrà sulla pagina "Login"
        binding.ConstraintLogin.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        userlvl = responseU.user!!.Livello
                        Toast.makeText(
                            context,
                            "Login effettuato con successo!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        getQRData(FirebaseAuth.getInstance().uid, object :
                            FireBaseCallbackCart {
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
                                                        putExtra("cart", responseC.cart)
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
                                                        putExtra("cart", responseC.cart)
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
                                                        putExtra("cart", responseC.cart)
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
                    }

                }, context)
            } else {
                view.findNavController().navigate(R.id.IntroToLogin)
            }
        }

        // Cliccando sul bottone, la navigazione porterà alla pagina "Registrati"
        binding.registrati.setOnClickListener {
            view.findNavController().navigate(R.id.IntroToRegister)
        }
    }
}