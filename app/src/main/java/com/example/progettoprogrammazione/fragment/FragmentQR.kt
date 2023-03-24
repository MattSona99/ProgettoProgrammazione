package com.example.progettoprogrammazione.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentQrBinding

class FragmentQR : Fragment() {

    private lateinit var binding : FragmentQrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrBinding.inflate(layoutInflater)

        val args = this.arguments
        val qrcode = args?.getParcelable<Bitmap>("qrcode") as Bitmap
        binding.qrcodeimage.setImageBitmap(qrcode)

        return binding.root
    }
}