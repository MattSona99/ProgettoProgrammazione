package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityLoginBinding
import com.example.progettoprogrammazione.databinding.ActivityRegistratiBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ConstraintEntra.setOnClickListener() {
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()

            if (Email.isNotEmpty() && Password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login effettuato con successo!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Email e password non corrispondono!", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG).show()
            }
        }

        binding.noaccount.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}