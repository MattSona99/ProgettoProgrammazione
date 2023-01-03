package com.example.progettoprogrammazione.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoprogrammazione.models.Product

class ProductViewModel :ViewModel() {
    val arrayListProdottiLiveData = MutableLiveData<ArrayList<Product>>()
}