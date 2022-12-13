package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.intro.FragmentLogin
import com.example.progettoprogrammazione.intro.FragmentRegister

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
}