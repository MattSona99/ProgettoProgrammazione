package com.example.progettoprogrammazione.models

var restaurantList = mutableListOf<Restaurant>()

val RESTAURANT_EXTRA="restaurantExtra"

class Restaurant(
    var image_r: Int,
    var nome_r: String,
    var descrizione_r: String,
    val id: Int? = restaurantList.size
)

