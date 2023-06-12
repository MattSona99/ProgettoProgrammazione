package com.example.progettoprogrammazione.activity

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityIntroBinding
import com.example.progettoprogrammazione.databinding.ActivityRestaurateurBinding

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa activity consente di navigare tra le pagine per effettuare il login o la registrazione

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    // Inizializzazione dell'activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Costruendo l'appbar in questo modo, eviteremo di mostrare la back arrow per tornare indietro durante la navigazione
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.fragmentIntro,
                R.id.fragmentLogin,
                R.id.fragmentRegister
            )
            .build()

        // Inizializziamo il controllo per la navigazione all'interno di quest'activity
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHost.id) as NavHostFragment

        navController = navHostFragment.findNavController()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // Crea un override sul controllo dei tocchi al di fuori delle componenti in focus
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