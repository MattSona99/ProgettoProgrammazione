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
import com.example.progettoprogrammazione.models.RESTAURANT_EXTRA
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.restaurantList
import com.example.progettoprogrammazione.ristorante.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), RestaurantClickListener {

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

        getRestaurantData()
        user = FirebaseAuth.getInstance()

        replaceFragment(fragmentRistoranti)

        binding.navbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_dashboard -> replaceFragment(fragmentRistoranti)
                R.id.ic_profile -> replaceFragment(fragmentProfilo)
                R.id.ic_logout -> {
                    user.signOut()
                    Toast.makeText(this, "Logout effettuato con successo", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        //FUNZIONE CHE SHOWA I RISTORANTI
        val mainActivity = this
        bindingRist.recycleView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = RestaurantAdapter(restaurantList, mainActivity)
        }

    }

    private fun getRestaurantData() {
        val r1 = Restaurant(R.drawable.pencil, "pino little italy", "descrizione pino")
        restaurantList.add(r1)
        val r2 = Restaurant(R.drawable.pencil, "poldo pizza", "descrizione poldo")
        restaurantList.add(r2)
        val r3 = Restaurant(R.drawable.pencil, "la vecchia osteria", "descrizione pino")
        restaurantList.add(r3)

    }

    override fun onClick(restaurant: Restaurant) {
        val intent = Intent(applicationContext, RestaurantDetail::class.java)
        intent.putExtra(RESTAURANT_EXTRA, restaurant.id)
        startActivity(intent)

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}