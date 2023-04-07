package com.example.progettoprogrammazione.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentQrBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.utils.ResponseShoppingCart
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.io.ByteArrayOutputStream

class FragmentQR : Fragment(), ShoppingCartUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var binding: FragmentQrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrBinding.inflate(layoutInflater)

        getQRData(firebaseAuth.uid, object : FireBaseCallbackShoppingCart {
            override fun onResponse(responseC: ResponseShoppingCart) {
                val multiFormatWriter = MultiFormatWriter()
                val bitMatrix: BitMatrix = multiFormatWriter.encode(responseC.qrcode, BarcodeFormat.QR_CODE, 500, 500)
                val qrCodeBitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
                for (x in 0 until bitMatrix.width) {
                    for (y in 0 until bitMatrix.height) {
                        qrCodeBitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                    }
                }
                binding.qrcodeimage.setImageBitmap(qrCodeBitmap)
            }

        }, context)

        return binding.root
    }

}