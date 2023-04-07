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
import com.example.progettoprogrammazione.databinding.FragmentIntroBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackQRCode
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentIntro : Fragment(), UserUtils, RestaurantUtils, ProductUtils, QRCodeUtils {

    private lateinit var binding: FragmentIntroBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    var userlvl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            FireBaseCallbackQRCode {
                            override fun onResponse(responseC: ResponseQRCode) {
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
                                                        putExtra("qrcode", responseC.qrcode)
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
                                                        putExtra("qrcode", responseC.qrcode)
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
                                                        putExtra("qrcode", responseC.qrcode)
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
        binding.registrati.setOnClickListener {
            view.findNavController().navigate(R.id.IntroToRegister)
        }
    }
}