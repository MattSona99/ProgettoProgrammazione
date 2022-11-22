package com.example.progettoprogrammazione.activity

var resturantList = mutableListOf<Resturant>()

val RESTURANT_EXTRA="resturantExtra"

class Resturant(
    var image_r: Int,
    var nome_r: String,
    var descrizione_r: String,
    val id: Int? = resturantList.size
)

