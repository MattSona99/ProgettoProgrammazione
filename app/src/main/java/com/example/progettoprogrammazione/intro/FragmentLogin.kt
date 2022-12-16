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
import com.example.progettoprogrammazione.activity.UserActivity
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.databinding.FragmentLoginBinding
import com.example.progettoprogrammazione.utils.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.UserUtil
import com.example.progettoprogrammazione.utils.ResponseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentLogin : Fragment(), UserUtil {

    private lateinit var binding: FragmentLoginBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var userlvl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ConstraintEntra.setOnClickListener() {
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        getUserData(object : FireBaseCallbackUser {
                            override fun onResponse(response: ResponseUser) {
                                userlvl = response.user!!.Livello

                                Toast.makeText(
                                    context,
                                    "Login effettuato con successo!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()

                                when (userlvl) {
                                    "1" -> {
                                        val intent = Intent(context, UserActivity::class.java).apply {
                                                putExtra("user", response.user)
                                            }
                                        startActivity(intent)
                                        activity?.finish()
                                    }
                                    "2" -> {
                                        val intent = Intent(context, EmployeeActivity::class.java).apply {
                                                putExtra("user", response.user)
                                            }
                                        startActivity(intent)
                                        activity?.finish()
                                    }
                                    "3" -> {
                                        val intent = Intent(context, RestaurateurActivity::class.java).apply {
                                                putExtra("user", response.user)
                                            }
                                        startActivity(intent)
                                        activity?.finish()
                                    }
                                    else -> {
                                        Toast.makeText(
                                            context,
                                            "Email e password non corrispondono!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }, context)
                    }
                }
            } else {
                Toast.makeText(context, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG).show()
            }
        }

        binding.noaccount.setOnClickListener {
            view.findNavController().navigate(R.id.LoginToRegister)
        }

    }
}
