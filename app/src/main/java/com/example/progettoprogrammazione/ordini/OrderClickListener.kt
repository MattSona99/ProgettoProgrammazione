package com.example.progettoprogrammazione.ordini

import com.example.progettoprogrammazione.models.Order

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa interfaccia consente di cliccare un ordine specifico

interface OrderClickListener{
    fun onClickOrder(order: Order)
}