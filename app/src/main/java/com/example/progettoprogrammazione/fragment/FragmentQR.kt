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
import androidx.navigation.navGraphViewModels
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentQrBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackQRCode
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.ResponseQRCode
import com.example.progettoprogrammazione.utils.QRCodeUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class FragmentQR : Fragment(), QRCodeUtils {

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var binding: FragmentQrBinding
    private lateinit var user: User

    private lateinit var cartViewModel: CartViewModel
    private val cartViewModelR: CartViewModel by navGraphViewModels(R.id.nav_restaurateur)
    private val cartViewModelU: CartViewModel by navGraphViewModels(R.id.nav_user)
    private val cartViewModelD: CartViewModel by navGraphViewModels(R.id.nav_dipendente)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User
        when (user.Livello) {
            "1" -> cartViewModel = cartViewModelU
            "2" -> cartViewModel = cartViewModelD
            "3" -> cartViewModel = cartViewModelR
        }

        getQRData(firebaseAuth.uid, object : FireBaseCallbackQRCode {
            override fun onResponse(responseC: ResponseQRCode) {
                if (responseC.qrcode == "null") {
                    binding.layoutQr.isGone = true
                } else {
                    cartViewModel.deleteCartItems()
                    binding.noQrcode.isGone = true
                    val multiFormatWriter = MultiFormatWriter()
                    val bitMatrix: BitMatrix =
                        multiFormatWriter.encode(responseC.qrcode, BarcodeFormat.QR_CODE, 500, 500)
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
                }
            }

        }, context)

        return binding.root
    }

}