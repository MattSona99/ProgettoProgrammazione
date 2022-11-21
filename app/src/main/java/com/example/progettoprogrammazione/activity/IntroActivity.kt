package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.progettoprogrammazione.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val loginbtn = findViewById<ConstraintLayout>(R.id.ConstraintLogin)
        loginbtn.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity (intent)
        }

        val registerbtn = findViewById<TextView>(R.id.registrati)
        registerbtn.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}