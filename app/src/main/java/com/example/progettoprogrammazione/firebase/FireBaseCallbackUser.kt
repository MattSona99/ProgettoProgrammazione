package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseUser


// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Interfaccia con una funzione per effettuare una callback e poter controllare un utente


interface FireBaseCallbackUser {
    fun onResponse(responseU: ResponseUser)
}