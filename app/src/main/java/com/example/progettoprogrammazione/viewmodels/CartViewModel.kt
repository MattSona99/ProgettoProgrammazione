package com.example.progettoprogrammazione.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoprogrammazione.models.CartProduct

class CartViewModel : ViewModel() , java.io.Serializable {
    private val cartProductItems = MutableLiveData<ArrayList<CartProduct>>(ArrayList())

    fun getcartItems(): LiveData<ArrayList<CartProduct>> {
        return cartProductItems
    }

    fun addcartItems(cartProduct: CartProduct) {
        val carts = cartProductItems.value ?: ArrayList()
        carts.add(cartProduct)
        cartProductItems.value = carts
    }

}