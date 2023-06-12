package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Order

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci

data class ResponseOrder(
    val ordini: ArrayList<Order> = arrayListOf()
)
