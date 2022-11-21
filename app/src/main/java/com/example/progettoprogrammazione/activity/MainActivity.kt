package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityLoginBinding
import com.example.progettoprogrammazione.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        if (user.currentUser != null) {
            user.currentUser?.let {
                binding.userEmail.text = it.email
            }
        }

        binding.ConstraintLogout.setOnClickListener() {
            user.signOut()
            Toast.makeText(this, "Logout effettuato con successo!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }


    }
}