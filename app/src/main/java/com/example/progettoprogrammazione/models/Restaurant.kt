package com.example.progettoprogrammazione.models

var restaurantList = mutableListOf<Restaurant>()

val RESTAURANT_EXTRA="restaurantExtra"

data class Restaurant(
    var image_r: Int,
    var nome_r : String,
    var descrizioneR: String,
    val id: Int? = restaurantList.size
)
