package com.example.progettoprogrammazione.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityCreaMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class CreaMenu : AppCompatActivity(), ProductUtils, RestaurantUtils {

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivityCreaMenuBinding

    private var pressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreaMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra("user") as User?
        val ristorante = intent.getParcelableExtra<Restaurant>("ristorante")
        val restName = ristorante?.idR

        binding.btnBevanda.setOnClickListener {
            showDialog("bevanda", restName)
        }

        binding.btnAntipasto.setOnClickListener {
            showDialog("antipasto", restName)
        }

        binding.btnPrimo.setOnClickListener {
            showDialog("primo", restName)
        }

        binding.btnSecondo.setOnClickListener {
            showDialog("secondo", restName)
        }

        binding.btnContorno.setOnClickListener {
            showDialog("contorno", restName)
        }

        binding.btnDolce.setOnClickListener {
            showDialog("dolce", restName)
        }

        binding.constraintfine.setOnClickListener {
            getRestaurantData(object : FireBaseCallbackRestaurant {
                override fun onResponse(responseR: ResponseRistorante) {
                    val intent = Intent(this@CreaMenu, RestaurateurActivity::class.java)
                    intent.putExtra("user", user)
                    intent.putExtra("ristoranti", responseR.ristoranti)
                    startActivity(intent)
                    finish()
                }
            }, this)
        }
    }

    private fun showDialog(add: String, restName: String?) {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.fragment_add_to_menu, null)
        val addDialog = AlertDialog.Builder(this)
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        val descrizioneP = v.findViewById<EditText>(R.id.descrizione_prodotto)

        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { _, _ ->
            val nomePbind = nomeP.text.toString()
            val prezzoPbind = prezzoP.text.toString()
            val descrizionePbind = descrizioneP.text.toString()
            val pData = Product(
                nomePbind,
                prezzoPbind,
                descrizionePbind,
                UUID.randomUUID().toString()
            )
            when (add) {
                "bevanda" -> addBevanda(restName, pData, this)
                "antipasto" -> addAntipasto(restName, pData, this)
                "primo" -> addPrimo(restName, pData, this)
                "secondo" -> addSecondo(restName, pData, this)
                "contorno" -> addContorno(restName, pData, this)
                "dolce" -> addDolce(restName, pData, this)
            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
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