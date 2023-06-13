package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.CartProduct

class calcolatotale_class {

    fun calcolaTotale(cartItems: ArrayList<CartProduct>): Float {
        var ptot: Float = 0f

        for (c in cartItems) {
            ptot = ptot.plus(c.totPrice ?: 0f)
        }
        return ptot
    }
}