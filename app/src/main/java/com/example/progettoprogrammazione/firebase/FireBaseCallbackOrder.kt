package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseOrder

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare una lista di ordini

interface FireBaseCallbackOrder {
    fun onResponse(responseO: ResponseOrder)
}