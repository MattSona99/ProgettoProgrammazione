package com.example.progettoprogrammazione.firebase

import com.example.progettoprogrammazione.utils.ResponseDipendente

interface FireBaseCallbackDipendente {
    fun onResponse(responseD: ResponseDipendente)
}