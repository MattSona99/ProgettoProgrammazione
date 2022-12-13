package com.example.progettoprogrammazione.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityUserBinding
import com.example.progettoprogrammazione.fragment.FragmentProfilo
import com.example.progettoprogrammazione.fragment.FragmentRistoranti
//import com.example.progettoprogrammazione.models.RESTAURANT_EXTRA
//import com.example.progettoprogrammazione.models.restaurantList
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class UserActivity : AppCompatActivity() {

    private val fragmentProfilo = FragmentProfilo()
    private val fragmentRistoranti = FragmentRistoranti()

    private lateinit var binding: ActivityUserBinding

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
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
                    finish()
                }
            }
            true
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_UP) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
/*
    override fun onClickResturant(restaurant: Restaurant) {

        val intent = Intent(applicationContext, RestaurantDetail::class.java)
        intent.putExtra(RESTAURANT_EXTRA, restaurant.id)
        startActivity(intent)
    }

 */
}