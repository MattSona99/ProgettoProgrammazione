package com.example.progettoprogrammazione.models

val userList = mutableListOf<User>()

val USER_EXTRA = "userExtra"

class User(
    var Nome: String,
    var Cognome: String,
    var Email: String,
    var Password: String,
    var Telefono: String,
    var Uri: String,
    var Livello: String,
    val id: Int? = userList.size
)


