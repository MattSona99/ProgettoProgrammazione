package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityMainBinding
import com.example.progettoprogrammazione.fragment.FragmentProfilo
import com.example.progettoprogrammazione.fragment.FragmentRistoranti
//import com.example.progettoprogrammazione.models.RESTAURANT_EXTRA
import com.example.progettoprogrammazione.models.Restaurant
//import com.example.progettoprogrammazione.models.restaurantList
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity()
//    , RestaurantClickListener
{

    private val fragmentProfilo = FragmentProfilo()
    private val fragmentRistoranti = FragmentRistoranti()

    private lateinit var binding: ActivityMainBinding

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        replaceFragment(fragmentRistoranti)

        binding.navbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_dashboard -> replaceFragment(fragmentRistoranti)
                R.id.ic_profile -> {
                    replaceFragment(fragmentProfilo)
/*                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    
 */
                }
                R.id.ic_logout -> {
                    user.signOut()
                    Toast.makeText(this, "Logout effettuato con successo", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    fun onClick(restaurant: Restaurant) {
/*        val intent = Intent(applicationContext, RestaurantDetail::class.java)
        intent.putExtra(RESTAURANT_EXTRA, restaurant.id)
        startActivity(intent)
*/
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
