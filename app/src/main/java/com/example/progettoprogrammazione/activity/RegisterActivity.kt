package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.progettoprogrammazione.R


class RegisterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrati)

        val registerbtn = findViewById<ConstraintLayout>(R.id.ConstraintRegistrati)
        registerbtn.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity (intent)
        }

        val already = findViewById<TextView>(R.id.already)
        already.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}