package com.example.progettoprogrammazione.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.progettoprogrammazione.models.Restaurant

class RestaurantViewModel: ViewModel() {
    val arrayListRistorantiLiveData = MutableLiveData<ArrayList<Restaurant>>()

}
