package com.example.progettoprogrammazione.ristorante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.progettoprogrammazione.databinding.ActivityRestaurantDetailBinding
import com.example.progettoprogrammazione.models.RESTAURANT_EXTRA
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.restaurantList

class RestaurantDetail : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantID=intent.getIntExtra(RESTAURANT_EXTRA,-1)
        val restaurant=restaurantFromId(restaurantID)

        if(restaurant != null)
        {
            binding.copertina.setImageResource(restaurant.image_r)
            binding.nomeRistorante.text = restaurant.nome_r
            binding.descrizione.text = restaurant.descrizioneR

        }
    }

    private fun restaurantFromId(restaurantID: Int): Restaurant? {
        for (restaurant in restaurantList) {
            if(restaurant.id == restaurantID)
                return restaurant
        }
        return null
    }
}