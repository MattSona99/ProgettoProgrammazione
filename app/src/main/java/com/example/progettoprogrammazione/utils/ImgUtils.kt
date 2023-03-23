package com.example.progettoprogrammazione.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*

interface ImgUtils {
    fun selectImageFromGallery()

    fun uploadImage(imageUri: Uri): String {
        val fileName = UUID.randomUUID().toString() + ".jpg"

        val refStorage =
            FirebaseStorage.getInstance().getReference("Users-images/").child(fileName)

        refStorage.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    it.toString()
                }
            }

            .addOnFailureListener { e ->
                print(e.message)
            }
        return fileName
    }
}