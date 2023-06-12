package com.example.progettoprogrammazione.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityRestaurateurBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa activity consente di navigare tra le pagine create per le funzionalità di un utente di livello "3"

class RestaurateurActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurateurBinding

    private lateinit var user: FirebaseAuth

    private lateinit var cartViewModel: CartViewModel

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bundle: Bundle

    private var pressedTime = 0L

    // Inizializzazione dell'activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        //Prendiamo gli argomenti passati dalla Intro
        val u = intent.getParcelableExtra("user") as User?
        val cart = intent.getByteArrayExtra("cart") as BooleanArray?
        bundle = Bundle()
        bundle.putParcelable("user", u)
        bundle.putString("userlvl", u?.Livello)
        bundle.putBooleanArray("cart", cart)

        // Costruendo l'appbar in questo modo, eviteremo di mostrare la back arrow per tornare indietro durante la navigazione
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.fragmentRistoranti,
                R.id.fragmentProfilo,
                R.id.fragmentGestione
            )
            .build()

        // Inizializziamo il controllo per la navigazione all'interno di quest'activity
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.restaurateurNav.id) as NavHostFragment

        navController = navHostFragment.findNavController()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        //Effettuiamo il binding della navbar personalizzata
        binding.navbarRestaurateur.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_ristorantiR -> {
                    navController.navigate(R.id.Ristoranti_R)
                }
                R.id.ic_profileR -> {
                    navController.navigate(R.id.Profilo_R, bundle)
                }
                R.id.ic_gestioneR -> {
                    navController.navigate(R.id.Gestione_R, bundle)
                }
                R.id.ic_logoutR -> {
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

    // Crea un override sul controllo del click sul tasto indietro
    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(baseContext, "Premi indietro di nuovo per uscire.", Toast.LENGTH_SHORT)
                .show()
        }
        pressedTime = System.currentTimeMillis()
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

    // Effettua il binding della navbar superiore
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_cart -> navController.navigate(R.id.fragmentCarrello_R, bundle)
            R.id.ic_qrcode -> navController.navigate(R.id.fragmentQR_R, bundle)
        }
        return super.onOptionsItemSelected(item)
    }
}
