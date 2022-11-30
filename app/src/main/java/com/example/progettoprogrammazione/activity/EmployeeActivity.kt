package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityEmployeeBinding
import com.example.progettoprogrammazione.databinding.ActivityBasicuserBinding
import com.example.progettoprogrammazione.fragment.FragmentProfilo
import com.example.progettoprogrammazione.fragment.FragmentRistoranti
import com.google.firebase.auth.FirebaseAuth

class EmployeeActivity: AppCompatActivity() {

    private val fragmentProfilo = FragmentProfilo();
    private val fragmentRistoranti = FragmentRistoranti();

    private lateinit var binding: ActivityEmployeeBinding

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        //replaceFragment(fragmentRistoranti)

        //NUOVA NAVBAR?
        binding.navbarEmployee.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                //FUNZIONI VARIE
                R.id.ic_logout -> {
                    user.signOut()
                    Toast.makeText(this, "Logout effettuato con successo", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}