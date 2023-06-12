package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseRating

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare una lista di ratings


interface FireBaseCallbackRating {
    fun onResponse(responseR : ResponseRating)
}