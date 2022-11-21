package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ActionMenuView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityRegistratiBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistratiBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistratiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ConstraintRegistrati.setOnClickListener {
            val Nome = binding.nome.text.toString()
            val Cognome = binding.cognome.text.toString()
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()
            val Telefono = binding.telefono.text.toString()

            if (Nome.isNotEmpty() && Cognome.isNotEmpty() && Email.isNotEmpty() && Password.isNotEmpty() && Telefono.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Utente registrato con successo!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG).show()
            }
        }

        binding.already.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}