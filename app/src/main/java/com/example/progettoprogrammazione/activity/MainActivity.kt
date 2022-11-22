package com.example.progettoprogrammazione.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getResturantData()
        user = FirebaseAuth.getInstance()

        //FUNZIONE CHE SHOWA I RISTORANTI
        val mainActivity = this
        binding.recycleView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = ResturantAdapter(resturantList)
        }
    }

    private fun getResturantData() {
        val r1 = Resturant(R.drawable.pencil, "pino little italy", "descrizione pino")
        resturantList.add(r1)

        val r2 = Resturant(R.drawable.pencil, "poldo pizza", "descrizione poldo")
        resturantList.add(r2)

        val r3 = Resturant(R.drawable.pencil, "la vecchia osteria", "descrizione pino")
        resturantList.add(r3)

        val r4 = Resturant(R.drawable.pencil, "pino little italy", "descrizione pino")
        resturantList.add(r4)

        val r5 = Resturant(R.drawable.pencil, "poldo pizza", "descrizione poldo")
        resturantList.add(r5)

        val r6 = Resturant(R.drawable.pencil, "la vecchia osteria", "descrizione pino")
        resturantList.add(r6)



    }

}