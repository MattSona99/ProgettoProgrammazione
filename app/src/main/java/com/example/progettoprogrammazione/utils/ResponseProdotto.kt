package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Product

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci

data class ResponseProdotto(
    val prodotto: ArrayList<Product> = arrayListOf()
)