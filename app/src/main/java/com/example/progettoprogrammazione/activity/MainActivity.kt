package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityMainBinding
import com.example.progettoprogrammazione.databinding.FragmentRistorantiBinding
import com.example.progettoprogrammazione.fragment.FragmentProfilo
import com.example.progettoprogrammazione.fragment.FragmentRistoranti
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter
import com.example.progettoprogrammazione.ristorante.RestaurantClickListener
import com.example.progettoprogrammazione.ristorante.ResturantDetail
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() , RestaurantClickListener {

    private val fragmentProfilo = FragmentProfilo();
    private val fragmentRistoranti = FragmentRistoranti();

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingRist: FragmentRistorantiBinding

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingRist = FragmentRistorantiBinding.inflate(layoutInflater)

        getResturantData()
        user = FirebaseAuth.getInstance()

        replaceFragment(fragmentRistoranti)

        binding.navbar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_dashboard -> replaceFragment(fragmentRistoranti)
                R.id.ic_profile -> replaceFragment(fragmentProfilo)
                R.id.ic_logout -> {
                    user.signOut()
                    Toast.makeText(this, "Logout effettuato con successo", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,IntroActivity :: class.java)
                    startActivity(intent)
                }
            }
            true
        }

        //FUNZIONE CHE SHOWA I RISTORANTI
        val mainActivity = this
        bindingRist.recycleView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = RestaurantAdapter(resturantList,mainActivity)
        }

    }

    private fun getResturantData() {
        val r1 = Resturant(R.drawable.pencil, "pino little italy", "descrizione pino")
        resturantList.add(r1)
        val r2 = Resturant(R.drawable.pencil, "poldo pizza", "descrizione poldo")
        resturantList.add(r2)
        val r3 = Resturant(R.drawable.pencil, "la vecchia osteria", "descrizione pino")
        resturantList.add(r3)

    }

    override fun onClick(resturant: Resturant) {
        val intent= Intent(applicationContext, ResturantDetail::class.java)
        intent.putExtra(RESTURANT_EXTRA,resturant.id)
        startActivity(intent)

    }
    private fun replaceFragment(fragment : Fragment){
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}