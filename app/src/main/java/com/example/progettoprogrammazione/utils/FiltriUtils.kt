package com.example.progettoprogrammazione.utils

import android.widget.Filter
import com.example.progettoprogrammazione.models.Restaurant

interface FiltriUtils {

    fun searchFilter(restaurant: ArrayList<Restaurant>, p0: CharSequence?): ArrayList<Restaurant> {

        if (p0 == null || p0.isEmpty()) {
            return restaurant
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
