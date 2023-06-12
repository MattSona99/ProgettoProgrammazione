package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Restaurant

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa interfaccia implementa determinati metodi per effettuare dei filtri all'interno di una lista

interface FiltriUtils {

    // Funzione che permette di cercare una parola chiave all'interno dei nomi dei ristoranti presenti nella lista
    fun searchFilter(restaurant: ArrayList<Restaurant>, p0: CharSequence?): ArrayList<Restaurant> {

        if (p0 == null || p0.isEmpty()) {
            return arrayListOf()
        } else {
            val searchChar = p0.toString().lowercase()
            val restaurantFiltered = ArrayList<Restaurant>()

            for (ristorante in restaurant) {
                if (ristorante.nomeR!!.lowercase().contains(searchChar)) {
                    restaurantFiltered.add(ristorante)
                }
            }
            return restaurantFiltered
        }

    }

    // Funzione che permette di cercare una serie di ristoranti che hanno come tipologia di cibo il parametro passato
    fun typeFilter(restaurant: ArrayList<Restaurant>, tipo: String): ArrayList<Restaurant> {

        val filteredResults = ArrayList<Restaurant>()

        if (tipo == "rating") {
            for (ristorante in restaurant) {
                if (ristorante.ratingR > 3) filteredResults.add(ristorante)
            }
        } else if (tipo == "vegan") {
            for (ristorante in restaurant) {
                if (ristorante.veganR) filteredResults.add(ristorante)
            }
        } else {
            for (ristorante in restaurant) {
                if (ristorante.tipoCiboR!!.lowercase().contains(tipo)) {
                    filteredResults.add(ristorante)
                }
            }
        }

        filteredResults.sortedByDescending { it.ratingR }
        return filteredResults
    }


}
