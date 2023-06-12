package com.example.progettoprogrammazione.ristorante

import com.example.progettoprogrammazione.models.Restaurant

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa interfaccia consente di cliccare un ristorante specifico

interface RestaurantClickListener {
    fun onClickResturant(restaurant: Restaurant)
}
