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
import com.example.progettoprogrammazione.utils.FireBaseCallback
import com.example.progettoprogrammazione.utils.GetUserData
import com.example.progettoprogrammazione.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentIntro : Fragment(), GetUserData {

    private lateinit var binding: FragmentIntroBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

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
                }, context)
            } else {
                view.findNavController().navigate(R.id.IntroToLogin)
            }
        }
        binding.registrati.setOnClickListener() {
            view.findNavController().navigate(R.id.IntroToRegister)
        }
    }
}