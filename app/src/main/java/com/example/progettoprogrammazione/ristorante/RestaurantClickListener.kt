package com.example.progettoprogrammazione.ristorante

import com.example.progettoprogrammazione.models.Restaurant

interface RestaurantClickListener {
    fun onClick(restaurant: Restaurant)
}