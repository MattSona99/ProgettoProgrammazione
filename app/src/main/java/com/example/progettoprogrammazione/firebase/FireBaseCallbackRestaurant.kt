package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseRistorante

interface FireBaseCallbackRestaurant {
    fun onResponse(responseR: ResponseRistorante)
}