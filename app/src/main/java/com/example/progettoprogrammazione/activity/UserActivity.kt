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
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityUserBinding
import com.example.progettoprogrammazione.fragment.FragmentProfilo
import com.example.progettoprogrammazione.fragment.FragmentRistoranti
import com.example.progettoprogrammazione.models.User
//import com.example.progettoprogrammazione.models.RESTAURANT_EXTRA
//import com.example.progettoprogrammazione.models.restaurantList
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        var u = intent.getParcelableExtra("user") as User?
        var bundle = Bundle()
        bundle.putParcelable("user", u)

        binding.navbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_dashboard -> {
                    val navController = this.findNavController(R.id.user_nav)
                    navController.navigate(R.id.Ristoranti)
                }
                R.id.ic_profile -> {
                    val navController = this.findNavController(R.id.user_nav)
                    navController.navigate(R.id.Profilo, bundle)
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

}