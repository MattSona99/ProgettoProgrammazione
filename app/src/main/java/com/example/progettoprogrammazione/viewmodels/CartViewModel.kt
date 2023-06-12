package com.example.progettoprogrammazione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoprogrammazione.models.CartProduct

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa classe rende una lista di prodotti del carrello di tipo live data, in modo da poterla
// modificare temporaneamente all'interno dell'applicazione

class CartViewModel : ViewModel(), java.io.Serializable {
    private val cartProductItems = MutableLiveData<ArrayList<CartProduct>>(ArrayList())

    // Funzione che permette di recuperare la lista di prodotti
    fun getcartItems(): LiveData<ArrayList<CartProduct>> {
        return cartProductItems
    }

    // Funzione che permette di aggiungere alla lista un nuovo prodotto
    fun addcartItems(cartProduct: CartProduct) {
        val carts = cartProductItems.value ?: ArrayList()
        carts.add(cartProduct)
        cartProductItems.value = carts
    }

    // Funzione che elimina l'interno contenuto della lista
    fun deleteCartItems() {
        cartProductItems.value?.clear()
    }

    // Funzione che permette di eliminare un determinato prodotto all'interno della lista
    fun deleteSpecificCart(cartProduct: CartProduct) {
        val carts = cartProductItems.value ?: ArrayList()
        carts.remove(cartProduct)
        cartProductItems.value = carts
    }

    // Funzione che permette di modificare un determinato prodotto (non usata)
    fun modifySpecifiCart(cartProduct: CartProduct, quantity: String){
        val carts = cartProductItems.value ?: ArrayList()
        for(cart in carts){
            if(cart.pID == cartProduct.pID) {
                cartProduct.quantity = quantity.toInt()
            }
        }
        cartProductItems.value = carts
    }

}