package com.example.progettoprogrammazione.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityRestaurateurBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.viewmodels.ProductViewModel
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth

class RestaurateurActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurateurBinding

    private lateinit var user: FirebaseAuth

    private lateinit var resturantDataViewModel: RestaurantViewModel

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private var pressedTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        val u = intent.getParcelableExtra("user") as User?
        val bundle = Bundle()
        bundle.putParcelable("user", u)

        val r =
            intent.getParcelableArrayListExtra<Restaurant>("ristoranti") as ArrayList<Restaurant>

        resturantDataViewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]
        resturantDataViewModel.arrayListRistorantiLiveData.postValue(r)

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.fragmentRistoranti,
                R.id.fragmentProfilo,
                R.id.fragmentGestione
            )
            .build()

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.restaurateurNav.id) as NavHostFragment

        navController = navHostFragment.findNavController()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
