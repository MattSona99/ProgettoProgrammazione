package com.example.progettoprogrammazione.fragment

/*
          CartProduct("prod1", "desc 1", 1, 10.0f, "risto1", "id1"),
          CartProduct("prod2", "desc 2", 1, 20.0f, "risto2", "id2"),
          CartProduct("prod3", "desc 3", 1, 30.00f, "risto3", "id3"),
 */
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.utils.calcolatotale_class
import org.junit.Assert.assertEquals
import org.junit.Test


class CalcolaTotaleTest {

    @Test
    fun testCalcolaTotale() {
        // Crea un instanza della classe calcolatotale
        val calcolatotale_classInstance = calcolatotale_class()

        // Create a sample list of cart items
        val cartItems = arrayListOf(
            CartProduct("prod1", "desc 1", 1, 10.10f, "risto1", "id1"),
            CartProduct("prod2", "desc 2", 1, 20.20f, "risto2", "id2"),
            CartProduct("prod3", "desc 3", 1, 30.30f, "risto3", "id3"),
        )

        // chiama metodo calcolcaTotoale
        val result = calcolatotale_classInstance.calcolaTotale(cartItems)

        // Risultato test
        assertEquals(60.60f, result)

    /*
        //Case failure test

        val expectedTotal = 100.0f
        assertEquals(expectedTotal, result)

    */
    }
}
