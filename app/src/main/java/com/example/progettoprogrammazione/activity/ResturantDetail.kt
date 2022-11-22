package com.example.progettoprogrammazione.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityResturantDetailBinding

class ResturantDetail : AppCompatActivity() {

    private lateinit var binding: ActivityResturantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResturantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resturantID=intent.getIntExtra(RESTURANT_EXTRA,-1)
        val resturant=resturantFromId(resturantID)

        if(resturant != null)
        {
            binding.copertina.setImageResource(resturant.image_r)
            binding.nomeRistorante.text = resturant.nome_r
            binding.descrizione.text = resturant.descrizione_r

        }
    }

    private fun resturantFromId(resturantID: Int): Resturant? {
        for (resturant in resturantList) {
            if(resturant.id == resturantID)
                return resturant
        }
        return null
    }
}