package com.example.progettoprogrammazione.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questa interfaccia implementa determinati metodi per effettuare delle query con il database
// per la modifica dell'immagine profilo di un utente

interface ImgUtils {

    // Funzione che permette di selezionare un'immagine dalla galleria
    fun selectImageFromGallery()

    // Funzione che permette di caricare la nuova immagine all'interno del database
    fun uploadImage(imageUri: Uri, tipo: String): String {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refStorage = FirebaseStorage.getInstance().getReference("$tipo-images/").child(fileName)

        refStorage.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    it.toString()
                }
            }

            .addOnFailureListener { e ->
                print("Immagine non caricata nel database")
            }
        return fileName
    }
}