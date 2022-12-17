package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Restaurant

data class ResponseRistorante(
    var ristoranti: ArrayList<Restaurant> = arrayListOf()
)