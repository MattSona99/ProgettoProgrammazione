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
import com.example.progettoprogrammazione.activity.UserActivity
import com.example.progettoprogrammazione.databinding.FragmentIntroBinding
import com.example.progettoprogrammazione.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentIntro : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ConstraintLogin.setOnClickListener() {
            firebaseAuth = FirebaseAuth.getInstance()
            if (firebaseAuth.currentUser != null) {
                getUserData(object : FireBaseCallback {
                    override fun onResponse(response: Response) {
                        val intent = Intent(context, UserActivity::class.java).apply {
                            putExtra("user", response.user)
                        }
                        Toast.makeText(
                            context,
                            "Login effettuato con successo!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        startActivity(intent)
                    }
                })
            } else {
                view.findNavController().navigate(R.id.IntroToLogin)
            }
        }
        binding.registrati.setOnClickListener() {
            view.findNavController().navigate(R.id.IntroToRegister)
        }
    }

    //PRENDIAMO I DATI DEGLI USER
    private interface FireBaseCallback {
        fun onResponse(response: Response)
    }

    data class Response(
        var user: User? = null
    )

    private fun getUserData(callBack: FireBaseCallback) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        firebaseDatabase.getReference("Utenti").child(firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = Response()
                    response.user = User(
                        snapshot.child("Nome").value.toString(),
                        snapshot.child("Cognome").value.toString(),
                        snapshot.child("Email").value.toString(),
                        snapshot.child("Password").value.toString(),
                        snapshot.child("Telefono").value.toString(),
                        snapshot.child("Uri").value.toString(),
                        snapshot.child("Livello").value.toString()
                    )
                    callBack.onResponse(response)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        context,
                        "Errore durante il caricamento dei dati",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}