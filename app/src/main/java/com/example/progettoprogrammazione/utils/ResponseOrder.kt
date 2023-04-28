package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Order

data class ResponseOrder(
    val ordini: ArrayList<Order> = arrayListOf()
)
