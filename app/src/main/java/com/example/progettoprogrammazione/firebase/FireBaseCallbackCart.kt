package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseCart

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare un carrello

interface FireBaseCallbackCart {
    fun onResponse(responseC: ResponseCart)
}