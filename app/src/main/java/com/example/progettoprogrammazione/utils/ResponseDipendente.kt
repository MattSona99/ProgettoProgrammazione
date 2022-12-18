package com.example.progettoprogrammazione.utils

import com.example.progettoprogrammazione.models.Dipendente

data class ResponseDipendente(
    var dipendenti: ArrayList<Dipendente> = arrayListOf()
)
