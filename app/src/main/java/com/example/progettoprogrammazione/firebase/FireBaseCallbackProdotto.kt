package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseProdotto

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare una lista di prodotti

interface FireBaseCallbackProdotto {

    fun onResponse(responseP: ResponseProdotto)
}