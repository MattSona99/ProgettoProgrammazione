package com.example.progettoprogrammazione.ordini

import com.example.progettoprogrammazione.models.Order


interface OrderClickListener{
    fun onClickOrder(order: Order)
}