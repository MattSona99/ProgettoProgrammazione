package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseRistorante


// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare una lista di ristoranti

interface FireBaseCallbackRestaurant {
    fun onResponse(responseR: ResponseRistorante)
}