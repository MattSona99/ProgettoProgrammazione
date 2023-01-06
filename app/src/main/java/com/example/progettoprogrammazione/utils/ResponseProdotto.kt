package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Product

data class ResponseProdotto(
    val prodotto: ArrayList<Product> = arrayListOf()
)