package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Restaurant

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci

data class ResponseRistorante(
    var ristoranti: ArrayList<Restaurant> = arrayListOf()
)