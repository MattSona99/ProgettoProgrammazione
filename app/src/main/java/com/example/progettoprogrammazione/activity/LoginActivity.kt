package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.progettoprogrammazione.R


class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbtn = findViewById<ConstraintLayout>(R.id.ConstraintEntra)
        loginbtn.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity (intent)
        }

        val noaccount = findViewById<TextView>(R.id.noaccount)
        noaccount.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}