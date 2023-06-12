package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseDipendente

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare una lista di dipendenti

interface FireBaseCallbackDipendente {
    fun onResponse(responseD: ResponseDipendente)
}