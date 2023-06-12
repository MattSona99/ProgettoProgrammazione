package com.example.progettoprogrammazione.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.Fragment1QrBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackCart
import com.example.progettoprogrammazione.utils.CartUtils
import com.example.progettoprogrammazione.utils.ResponseCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix


// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
/* Questo fragment permette all'utente di mostrare a schermo, qualora ce ne fosse uno, il carrello
salvato nel database, restituendolo sotto forma di immagine QR-Code */

class FragmentQR : Fragment(), CartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var binding: Fragment1QrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1QrBinding.inflate(layoutInflater)

        binding.layoutQr.isGone = true
        binding.noQrcode.isGone = true

        // Richiama i dati del carrello utente e a seconda del risultato mostra a schermo il contenuto
        getQRData(firebaseAuth.uid, object : FireBaseCallbackCart {
            override fun onResponse(responseC: ResponseCart) {
                if (responseC.cart == "null") {
                    binding.layoutQr.isGone = true
                    binding.noQrcode.isVisible = true
                } else {
                    val multiFormatWriter = MultiFormatWriter()
                    val bitMatrix: BitMatrix =
                        multiFormatWriter.encode(responseC.cart, BarcodeFormat.QR_CODE, 500, 500)
                    val qrCodeBitmap = Bitmap.createBitmap(
                        bitMatrix.width,
                        bitMatrix.height,
                        Bitmap.Config.RGB_565
                    )
                    for (x in 0 until bitMatrix.width) {
                        for (y in 0 until bitMatrix.height) {
                            qrCodeBitmap.setPixel(
                                x,
                                y,
                                if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                            )
                        }
                    }
                    binding.qrcodeimage.setImageBitmap(qrCodeBitmap)
                    binding.noQrcode.isGone = true
                    binding.layoutQr.isVisible = true
                }
            }
        }, context)
        return binding.root
    }
}